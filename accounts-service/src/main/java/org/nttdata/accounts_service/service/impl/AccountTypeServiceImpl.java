package org.nttdata.accounts_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.repository.AccountTypeRepository;
import org.nttdata.accounts_service.service.IAccountTypeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements IAccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public AccountType findAccountTypeByName(AccountTypeEnum accountType) {
        return accountTypeRepository.findByName(accountType)
                .orElseThrow(() -> new NotFoundException("Account type don't exist"));
    }
}
