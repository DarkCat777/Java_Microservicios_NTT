package org.nttdata.accounts_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.accounts_service.domain.entity.AccountHolder;
import org.nttdata.accounts_service.domain.entity.key.AccountHolderId;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.repository.AccountHolderRepository;
import org.nttdata.accounts_service.service.feign.CustomerFeignClient;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceImplTest {

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private CustomerFeignClient customerService;

    @InjectMocks
    private AccountHolderServiceImpl accountHolderService;

    private Set<Long> holderIds;

    @BeforeEach
    void setUp() {
        holderIds = Set.of(1L, 2L, 3L);
    }

    @Test
    void testCreateAccountHoldersByAccountIdAndHolderIds_AllCustomersExist() {
        Long accountId = 1L;
        List<AccountHolder> accountHolders = List.of(
                new AccountHolder(new AccountHolderId(accountId, 1L)),
                new AccountHolder(new AccountHolderId(accountId, 2L)),
                new AccountHolder(new AccountHolderId(accountId, 3L))
        );

        when(customerService.existAllCustomerByIdsIn(holderIds)).thenReturn(true);
        when(accountHolderRepository.saveAll(anyList())).thenReturn(accountHolders);

        Set<AccountHolder> result = accountHolderService.createAccountHoldersByAccountIdAndHolderIds(accountId, holderIds);

        assertEquals(new HashSet<>(accountHolders), result);
        verify(customerService, times(1)).existAllCustomerByIdsIn(holderIds);
        verify(accountHolderRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreateAccountHoldersByAccountIdAndHolderIds_SomeCustomersDoNotExist() {
        Long accountId = 1L;

        when(customerService.existAllCustomerByIdsIn(holderIds)).thenReturn(false);

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> accountHolderService.createAccountHoldersByAccountIdAndHolderIds(accountId, holderIds));

        assertEquals("Invalid customerId", exception.getMessage());
        assertEquals(Map.of("customerId", "Some of customer's id don't exist."), exception.getValidations());
        verify(customerService, times(1)).existAllCustomerByIdsIn(holderIds);
        verify(accountHolderRepository, times(0)).saveAll(anyList());
    }
}
