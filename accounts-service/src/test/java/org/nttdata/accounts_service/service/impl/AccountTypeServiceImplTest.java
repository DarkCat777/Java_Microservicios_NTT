package org.nttdata.accounts_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.repository.AccountTypeRepository;

@ExtendWith(MockitoExtension.class)
class AccountTypeServiceImplTest {

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @InjectMocks
    private AccountTypeServiceImpl accountTypeService;
    private AccountTypeEnum accountTypeEnum;

    @BeforeEach
    void setUp() {
        AccountTypeEnum accountTypeEnum = AccountTypeEnum.SAVINGS;
    }

    @Test
    void testFindAccountTypeByName_Found() {
        AccountType accountType = new AccountType();
        accountType.setName(accountTypeEnum);

        when(accountTypeRepository.findByName(accountTypeEnum)).thenReturn(Optional.of(accountType));

        AccountType result = accountTypeService.findAccountTypeByName(accountTypeEnum);

        assertEquals(accountType, result);
        verify(accountTypeRepository, times(1)).findByName(accountTypeEnum);
    }

    @Test
    void testFindAccountTypeByName_NotFound() {
        when(accountTypeRepository.findByName(accountTypeEnum)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> accountTypeService.findAccountTypeByName(accountTypeEnum));

        assertEquals("Account type don't exist", exception.getMessage());
        verify(accountTypeRepository, times(1)).findByName(accountTypeEnum);
    }
}