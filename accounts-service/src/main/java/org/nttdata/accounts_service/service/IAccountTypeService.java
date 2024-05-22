package org.nttdata.accounts_service.service;

import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;

public interface IAccountTypeService {
    AccountType findAccountTypeByName(AccountTypeEnum accountType);
}
