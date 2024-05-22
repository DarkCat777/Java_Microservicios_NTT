package org.nttdata.accounts_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;
import org.nttdata.accounts_service.domain.entity.key.AccountAuthorizedSignatoryId;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.repository.AccountAuthorizedSignatoryRepository;
import org.nttdata.accounts_service.service.IAccountAuthorizedSignatoryService;
import org.nttdata.accounts_service.service.feign.CustomerFeignClient;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountAuthorizedSignatoryServiceImpl implements IAccountAuthorizedSignatoryService {

    private final AccountAuthorizedSignatoryRepository accountAuthorizedSignatoryRepository;

    private final CustomerFeignClient customerService;

    @Override
    public Set<AccountAuthorizedSignatory> createAccountSignatoriesByAccountIdAndSignatoryIds(Long accountId, Set<Long> authorizedSignatoryIds) {
        Boolean existAllCustomers = customerService.existAllCustomerByIdsIn(authorizedSignatoryIds);
        if (Boolean.TRUE.equals(existAllCustomers)) {
            return new HashSet<>(accountAuthorizedSignatoryRepository.saveAll(createAccountAuthorizedSignatoryInstance(accountId, authorizedSignatoryIds)));
        } else {
            throw new BusinessLogicValidationException("Invalid customerId", Map.of("customerId", "Some of customer's id don't exist."));
        }
    }

    private List<AccountAuthorizedSignatory> createAccountAuthorizedSignatoryInstance(Long accountId, Set<Long> authorizedSignatoryIds) {
        return authorizedSignatoryIds.stream()
                .map(authorizedSignatoryId -> new AccountAuthorizedSignatory(new AccountAuthorizedSignatoryId(accountId, authorizedSignatoryId)))
                .collect(Collectors.toList());
    }
}
