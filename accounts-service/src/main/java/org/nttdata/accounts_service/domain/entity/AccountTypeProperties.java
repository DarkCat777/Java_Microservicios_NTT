package org.nttdata.accounts_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.accounts_service.domain.type.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("account-type-properties")
public class AccountTypeProperties {
    @Id
    private String id;
    @Indexed(useGeneratedName = true, unique = true)
    private AccountType accountType;
    private Double maintenanceFee;
    private Long monthlyMovementLimit;
}
