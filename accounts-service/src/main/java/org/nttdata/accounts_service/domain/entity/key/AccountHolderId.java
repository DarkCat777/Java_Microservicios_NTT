package org.nttdata.accounts_service.domain.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountHolderId implements Serializable {
    /**
     * Referencia al accountId
     */
    private Long accountId;
    /**
     * Referencia al customerId que es titular
     */
    private Long holderId;
}