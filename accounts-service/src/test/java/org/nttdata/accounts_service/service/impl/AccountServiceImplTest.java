package org.nttdata.accounts_service.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.dto.TransactionDto;
import org.nttdata.accounts_service.domain.entity.Account;
import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.domain.type.BankProductType;
import org.nttdata.accounts_service.mapper.IAccountMapper;
import org.nttdata.accounts_service.repository.AccountRepository;
import org.nttdata.accounts_service.service.IAccountTypeService;
import org.nttdata.accounts_service.service.feign.TransactionFeignClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IAccountMapper accountMapper;

    @Mock
    private TransactionFeignClient transactionService;

    @Mock
    private IAccountTypeService accountTypeService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testGetAccountById() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        AccountDto accountDto = new AccountDto();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getAccountById(accountId);

        assertNotNull(result);
        assertEquals(accountDto, result);
        verify(accountRepository).findById(accountId);
        verify(accountMapper).toDto(account);
    }

    @Test
    void testGetAccountById_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getAccountById(accountId));
        verify(accountRepository).findById(accountId);
    }


    @Test
    void testDepositAccountById() {
        Long accountId = 1L;
        Double depositAmount = 100.0;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(100.0);
        AccountType accountType = new AccountType();
        accountType.setName(AccountTypeEnum.SAVINGS);
        accountType.setMonthlyMovementLimit(10);
        account.setAccountType(accountType);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountTypeService.findAccountTypeByName(AccountTypeEnum.SAVINGS)).thenReturn(accountType);
        when(transactionService.countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId)).thenReturn(1L);
        when(transactionService.createTransaction(any(TransactionDto.class))).thenReturn(new TransactionDto());
        when(accountMapper.toDto(any(Account.class))).thenReturn(new AccountDto());

        AccountDto result = accountService.depositAccountById(accountId, depositAmount);

        verify(accountRepository).findById(accountId);
        verify(transactionService).countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId);
        verify(transactionService).createTransaction(any(TransactionDto.class));
        verify(accountRepository).save(any(Account.class));
        verify(accountMapper).toDto(any(Account.class));
        assertNotNull(result);
    }

    @Test
    void testDepositAccountById_MonthlyLimitExceeded() {
        Long accountId = 1L;
        Double depositAmount = 100.0;
        Account account = new Account();
        account.setId(accountId);
        AccountType accountType = new AccountType();
        accountType.setName(AccountTypeEnum.SAVINGS);
        accountType.setMonthlyMovementLimit(10);
        account.setAccountType(accountType);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountTypeService.findAccountTypeByName(AccountTypeEnum.SAVINGS)).thenReturn(accountType);
        when(transactionService.countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId)).thenReturn(10L);

        assertThrows(BusinessLogicValidationException.class, () -> accountService.depositAccountById(accountId, depositAmount));
        verify(accountRepository).findById(accountId);
        verify(transactionService).countTransactionInCurrentMonth(BankProductType.ACCOUNT.name(), accountId);
    }

    @Test
    void testDeleteAccountById() {
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        doNothing().when(accountRepository).delete(account);

        accountService.deleteAccountById(accountId);

        verify(accountRepository).findById(accountId);
        verify(accountRepository).delete(account);
    }

    @Test
    void testDeleteAccountById_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.deleteAccountById(accountId));
        verify(accountRepository).findById(accountId);
    }
}