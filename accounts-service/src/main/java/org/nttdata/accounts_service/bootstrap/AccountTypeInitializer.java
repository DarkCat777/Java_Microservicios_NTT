package org.nttdata.accounts_service.bootstrap;

import lombok.RequiredArgsConstructor;
import org.nttdata.accounts_service.domain.entity.AccountType;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.repository.AccountTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInitializer implements CommandLineRunner {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        accountTypeRepository.deleteAll();
        List<AccountType> accountTypes = new ArrayList<>();
        accountTypes.add(new AccountType(
                null,
                AccountTypeEnum.CURRENT,
                1.0,
                Integer.MAX_VALUE
        ));
        accountTypes.add(new AccountType(
                null,
                AccountTypeEnum.FIXED_TERM,
                0.0,
                1
        ));
        accountTypes.add(new AccountType(
                null,
                AccountTypeEnum.SAVINGS,
                0.0,
                3
        ));
        accountTypeRepository.saveAll(accountTypes);
    }
}
