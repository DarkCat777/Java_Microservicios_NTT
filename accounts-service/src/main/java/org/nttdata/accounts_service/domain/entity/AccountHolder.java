package org.nttdata.accounts_service.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.accounts_service.domain.entity.key.AccountHolderId;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class AccountHolder {
    @EmbeddedId
    private AccountHolderId id;

    @ManyToOne
    @MapsId("accountId")
    private Account account;

    public AccountHolder(AccountHolderId id) {
        this.id = id;
    }
}
