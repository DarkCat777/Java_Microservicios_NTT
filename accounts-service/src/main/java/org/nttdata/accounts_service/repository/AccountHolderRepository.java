package org.nttdata.accounts_service.repository;

import org.nttdata.accounts_service.domain.entity.AccountHolder;
import org.nttdata.accounts_service.domain.entity.key.AccountHolderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, AccountHolderId> {
}