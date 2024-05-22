package org.nttdata.accounts_service.repository;

import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    Optional<AccountType> findByName(AccountTypeEnum name);
}