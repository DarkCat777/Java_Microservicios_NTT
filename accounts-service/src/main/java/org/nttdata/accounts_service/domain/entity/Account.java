package org.nttdata.accounts_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.accounts_service.domain.type.AccountType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Definición de la entidad cuentas como producto bancario
 *
 * @author Erick David Carpio Hachiri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String ownerId;
    private Double balance;
    private AccountType accountType;
    @CreatedDate
    private OffsetDateTime createdDate;
    @LastModifiedDate
    private OffsetDateTime lastModifiedDate;
    private Set<String> holderIds;
    private Set<String> authorizedSignatoryIds;
}
