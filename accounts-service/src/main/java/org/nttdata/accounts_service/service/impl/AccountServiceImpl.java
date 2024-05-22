package org.nttdata.accounts_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.dto.CustomerDto;
import org.nttdata.accounts_service.domain.dto.TransactionDto;
import org.nttdata.accounts_service.domain.entity.Account;
import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;
import org.nttdata.accounts_service.domain.entity.AccountHolder;
import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.domain.type.BankProductType;
import org.nttdata.accounts_service.domain.type.CustomerType;
import org.nttdata.accounts_service.domain.type.TransactionType;
import org.nttdata.accounts_service.mapper.IAccountMapper;
import org.nttdata.accounts_service.repository.AccountRepository;
import org.nttdata.accounts_service.service.*;
import org.nttdata.accounts_service.service.feign.CustomerFeignClient;
import org.nttdata.accounts_service.service.feign.TransactionFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.nttdata.accounts_service.domain.exception.NotFoundException.ACCOUNT_NOT_FOUND_TEMPLATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    private final IAccountMapper accountMapper;

    private final IAccountHolderService accountHolderService;

    private final IAccountAuthorizedSignatoryService accountAuthorizedSignatoryService;

    private final CustomerFeignClient customerService;

    private final TransactionFeignClient transactionService;

    private final IAccountTypeService accountTypeService;

    private Map<String, String> isValidCreateAccountOperation(CustomerDto customerDto, AccountDto accountDto) {
        switch (CustomerType.valueOf(customerDto.getCustomerType())) {
            case PERSONAL:
                Map<String, String> validations = new HashMap<>();
                if (Boolean.TRUE.equals(accountRepository.existsAccountByOwnerId(customerDto.getId()))) {
                    validations.put("Quantity of accounts", "You have more than one account (savings, current, fixed term) to be a personal client.");
                } else if (accountDto.getAuthorizedSignatoryIds() != null && !accountDto.getAuthorizedSignatoryIds().isEmpty()) {
                    validations.put("Authorized Signatory invalid", "Only enterprise customers can have authorized signatures.");
                } else if (accountDto.getHolderIds() != null && !accountDto.getHolderIds().isEmpty()) {
                    validations.put("Authorized Signatory invalid", "Only enterprise customers can have multiple holders.");
                }
                return validations;
            case BUSINESS:
                switch (AccountTypeEnum.valueOf(accountDto.getAccountType())) {
                    case CURRENT:
                        return Map.of();
                    case SAVINGS:
                    case FIXED_TERM:
                        return Map.of("Invalid account type", "As a business customer you can only create checking accounts.");
                    default:
                        return Map.of("Invalid customer type", "You can only create checking accounts with a business customer.");
                }
            default:
                return Map.of("Invalid customer type", "You can only create one personal or business account.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccountsByCustomerId(Long customerId) {
        return accountRepository.findAllByOwnerId(customerId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        CustomerDto customerDto = customerService.getCustomerById(accountDto.getOwnerId());
        Map<String, String> validations = this.isValidCreateAccountOperation(customerDto, accountDto);
        if (validations.isEmpty()) {
            Account accountEntity = accountMapper.toEntity(accountDto);
            AccountType accountType = accountTypeService.findAccountTypeByName(AccountTypeEnum.valueOf(accountDto.getAccountType()));
            accountEntity.setAccountType(accountType);
            // Save first data without relationships
            accountEntity = accountRepository.save(accountEntity);
            if (accountDto.getHolderIds() != null && !accountDto.getHolderIds().isEmpty()) {
                Set<AccountHolder> accountHolders = this.accountHolderService.createAccountHoldersByAccountIdAndHolderIds(accountEntity.getId(), accountDto.getHolderIds());
                accountEntity.setHolders(accountHolders);
            }
            if (accountDto.getAuthorizedSignatoryIds() != null && !accountDto.getAuthorizedSignatoryIds().isEmpty()) {
                Set<AccountAuthorizedSignatory> authorizedSignatories = this.accountAuthorizedSignatoryService.createAccountSignatoriesByAccountIdAndSignatoryIds(accountEntity.getId(), accountDto.getAuthorizedSignatoryIds());
                accountEntity.setAuthorizedSignatories(authorizedSignatories);
            }
            // Update second data with relationships
            accountEntity = accountRepository.save(accountEntity);
            return accountMapper.toDto(accountEntity);
        } else {
            throw new BusinessLogicValidationException("Business logic invalid", validations);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId)));
    }

    @Override
    @Transactional
    public AccountDto updateAccountById(Long accountId, AccountDto accountDto) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId)));
        account = accountMapper.partialUpdate(accountDto, account);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountDto depositAccountById(Long accountId, Double depositAmount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId)));
        AccountType accountType = accountTypeService.findAccountTypeByName(account.getAccountType().getName());
        // Get quantity transaction of this month
        Long transactionInCurrentMonth = transactionService.countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId);
        if (accountType.getMonthlyMovementLimit() > transactionInCurrentMonth) {
            // Do transactions
            account.setBalance(account.getBalance() + depositAmount);
            transactionService.createTransaction(new TransactionDto(null, depositAmount, TransactionType.DEPOSIT.name(), BankProductType.ACCOUNT.name(), accountId, null, null));
            account = accountRepository.save(account);
            return accountMapper.toDto(account);
        } else {
            throw new BusinessLogicValidationException("Monthly movement exceded", Map.of("Invalid transaction", "You have exceeded the number of monthly transactions allowed"));
        }
    }

    @Override
    @Transactional
    public AccountDto withdrawalAccountById(Long accountId, Double withdrawalAmount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId)));
        AccountType accountType = accountTypeService.findAccountTypeByName(account.getAccountType().getName());
        // Get quantity transaction of this month
        Long transactionInCurrentMonth = transactionService.countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId);
        if (accountType.getMonthlyMovementLimit() > transactionInCurrentMonth) {
            // Do transactions
            if (account.getBalance() >= withdrawalAmount) {
                account.setBalance(account.getBalance() - withdrawalAmount);
                transactionService.createTransaction(new TransactionDto(null, withdrawalAmount, TransactionType.WITHDRAWAL.name(), BankProductType.ACCOUNT.name(), accountId, null, null));
                return accountMapper.toDto(accountRepository.save(account));
            } else {
                throw new BusinessLogicValidationException("Withdrawal Amount", Map.of("Invalid Withdrawal Amount", "Withdrawal amount exceeds balance limit"));
            }
        } else {
            throw new BusinessLogicValidationException("Monthly movement exceded", Map.of("Invalid transaction", "You have exceeded the number of monthly transactions allowed"));
        }
    }

    @Override
    @Transactional
    public void deleteAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId)));
        accountRepository.delete(account);
    }
}
