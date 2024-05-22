package org.nttdata.accounts_service.service;

import org.nttdata.accounts_service.domain.entity.AccountHolder;

import java.util.Set;

public interface IAccountHolderService {
    Set<AccountHolder> createAccountHoldersByAccountIdAndHolderIds(Long accountId, Set<Long> holderIds);
}
