package org.nttdata.accounts_service.repository;

import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;
import org.nttdata.accounts_service.domain.entity.key.AccountAuthorizedSignatoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAuthorizedSignatoryRepository extends JpaRepository<AccountAuthorizedSignatory, AccountAuthorizedSignatoryId> {
}