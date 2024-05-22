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
import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;
import org.nttdata.accounts_service.domain.entity.key.AccountAuthorizedSignatoryId;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.repository.AccountAuthorizedSignatoryRepository;
import org.nttdata.accounts_service.service.feign.CustomerFeignClient;

@ExtendWith(MockitoExtension.class)
class AccountAuthorizedSignatoryServiceImplTest {

    @Mock
    private AccountAuthorizedSignatoryRepository accountAuthorizedSignatoryRepository;

    @Mock
    private CustomerFeignClient customerService;

    @InjectMocks
    private AccountAuthorizedSignatoryServiceImpl accountAuthorizedSignatoryService;

    private Long accountId;
    private Set<Long> authorizedSignatoryIds;
    @BeforeEach
    void setUp() {
        accountId = 1L;
        authorizedSignatoryIds = Set.of(1L, 2L, 3L);
    }

    @Test
    void testCreateAccountSignatoriesByAccountIdAndSignatoryIds_AllCustomersExist() {
        List<AccountAuthorizedSignatory> accountSignatories = List.of(
                new AccountAuthorizedSignatory(new AccountAuthorizedSignatoryId(accountId, 1L)),
                new AccountAuthorizedSignatory(new AccountAuthorizedSignatoryId(accountId, 2L)),
                new AccountAuthorizedSignatory(new AccountAuthorizedSignatoryId(accountId, 3L))
        );

        when(customerService.existAllCustomerByIdsIn(authorizedSignatoryIds)).thenReturn(true);
        when(accountAuthorizedSignatoryRepository.saveAll(anyList())).thenReturn(accountSignatories);

        Set<AccountAuthorizedSignatory> result = accountAuthorizedSignatoryService.createAccountSignatoriesByAccountIdAndSignatoryIds(accountId, authorizedSignatoryIds);

        assertEquals(new HashSet<>(accountSignatories), result);
        verify(customerService, times(1)).existAllCustomerByIdsIn(authorizedSignatoryIds);
        verify(accountAuthorizedSignatoryRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreateAccountSignatoriesByAccountIdAndSignatoryIds_SomeCustomersDoNotExist() {
        when(customerService.existAllCustomerByIdsIn(authorizedSignatoryIds)).thenReturn(false);

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> {
            accountAuthorizedSignatoryService.createAccountSignatoriesByAccountIdAndSignatoryIds(accountId, authorizedSignatoryIds);
        });

        assertEquals("Invalid customerId", exception.getMessage());
        assertEquals(Map.of("customerId", "Some of customer's id don't exist."), exception.getValidations());
        verify(customerService, times(1)).existAllCustomerByIdsIn(authorizedSignatoryIds);
        verify(accountAuthorizedSignatoryRepository, times(0)).saveAll(anyList());
    }
}
