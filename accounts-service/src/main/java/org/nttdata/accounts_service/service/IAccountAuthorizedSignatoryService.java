package org.nttdata.accounts_service.service;

import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;

import java.util.Set;

public interface IAccountAuthorizedSignatoryService {
    Set<AccountAuthorizedSignatory> createAccountSignatoriesByAccountIdAndSignatoryIds(Long accountId, Set<Long> authorizedSignatoryIds);
}
