package org.nttdata.accounts_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.dto.CustomerDto;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.nttdata.accounts_service.domain.type.AccountType;
import org.nttdata.accounts_service.domain.type.CustomerType;
import org.nttdata.accounts_service.mapper.IAccountMapper;
import org.nttdata.accounts_service.repository.AccountRepository;
import org.nttdata.accounts_service.service.IAccountService;
import org.nttdata.accounts_service.service.ICustomerRetrofitService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

import static org.nttdata.accounts_service.domain.exception.NotFoundException.ACCOUNT_NOT_FOUND_TEMPLATE;
import static org.nttdata.accounts_service.domain.exception.NotFoundException.CUSTOMER_NOT_FOUND_TEMPLATE;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final IAccountMapper accountMapper;
    private final ICustomerRetrofitService customerRetrofitClient;

    @Override
    public Flux<AccountDto> getAllAccountsByCustomerId(String customerId) {
        return accountRepository.findAllByOwnerId(customerId)
                .publishOn(Schedulers.boundedElastic())
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<AccountDto> createAccount(AccountDto accountDto) {
        return customerRetrofitClient.getCustomerById(accountDto.getOwnerId())
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, accountDto.getOwnerId())))
                ).flatMap(customerDto -> {
                    Map<String, String> validations = this.isValidCreateAccountOperation(customerDto, accountDto);
                    if (validations.isEmpty()) {
                        return accountRepository.save(accountMapper.toEntity(accountDto))
                                .publishOn(Schedulers.boundedElastic())
                                .map(accountMapper::toDto);
                    } else {
                        return Mono.error(new BusinessLogicValidationException("Business logic invalid", validations));
                    }
                });
    }

    private Map<String, String> isValidCreateAccountOperation(CustomerDto customerDto, AccountDto accountDto) {
        switch (CustomerType.valueOf(customerDto.getCustomerType())) {
            case PERSONAL:
                Boolean count = accountRepository.existsAccountByOwnerId(customerDto.getId()).block();
                if (Boolean.TRUE.equals(count)) {
                    return Map.of();
                } else {
                    return Map.of("Quantity of accounts", "You have more than one account (savings, current, fixed term) to be a personal client.");
                }
            case BUSINESS:
                switch (AccountType.valueOf(accountDto.getAccountType())) {
                    case CURRENT:
                        return Map.of();
                    case SAVINGS:
                    case FIXED_TERM:
                        return Map.of("Invalid account type", "You can only create one personal or business account.");
                    default:
                        return Map.of("Invalid customer type", "You can only create checking accounts with a business customer.");
                }
            default:
                return Map.of("Invalid customer type", "You can only create one personal or business account.");
        }
    }

    @Override
    public Mono<AccountDto> getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId))))
                .publishOn(Schedulers.boundedElastic())
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<AccountDto> updateAccount(String accountId, AccountDto accountDto) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId))))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(account -> accountRepository.save(accountMapper.partialUpdate(accountDto, account)))
                .map(accountMapper::toDto);
    }

    @Override
    public Mono<Void> deleteAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(ACCOUNT_NOT_FOUND_TEMPLATE, accountId))))
                .flatMap(accountRepository::delete);
    }
}
