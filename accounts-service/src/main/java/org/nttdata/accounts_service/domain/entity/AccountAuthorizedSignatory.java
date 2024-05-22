package org.nttdata.accounts_service.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nttdata.accounts_service.domain.entity.key.AccountAuthorizedSignatoryId;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "account_authorized_signatory")
public class AccountAuthorizedSignatory {
    @EmbeddedId
    private AccountAuthorizedSignatoryId id;

    @MapsId("accountId")
    @ManyToOne
    private Account account;

    public AccountAuthorizedSignatory(AccountAuthorizedSignatoryId id) {
        this.id = id;
    }
}