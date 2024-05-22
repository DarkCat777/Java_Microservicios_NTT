package org.nttdata.accounts_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.accounts_service.domain.entity.AccountHolder;
import org.nttdata.accounts_service.domain.entity.key.AccountHolderId;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.repository.AccountHolderRepository;
import org.nttdata.accounts_service.service.IAccountHolderService;
import org.nttdata.accounts_service.service.feign.CustomerFeignClient;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountHolderServiceImpl implements IAccountHolderService {

    private final AccountHolderRepository accountHolderRepository;

    private final CustomerFeignClient customerService;

    @Override
    public Set<AccountHolder> createAccountHoldersByAccountIdAndHolderIds(Long accountId, Set<Long> holderIds) {
        Boolean existAllCustomers = customerService.existAllCustomerByIdsIn(holderIds);
        if (Boolean.TRUE.equals(existAllCustomers)) {
            return new HashSet<>(accountHolderRepository.saveAll(createAccountHolderInstance(accountId, holderIds)));
        } else {
            throw new BusinessLogicValidationException("Invalid customerId", Map.of("customerId", "Some of customer's id don't exist."));
        }
    }

    private List<AccountHolder> createAccountHolderInstance(Long accountId, Set<Long> holderIds) {
        return holderIds.stream()
                .map(holderId -> new AccountHolder(new AccountHolderId(accountId, holderId)))
                .collect(Collectors.toList());
    }
}
