package org.nttdata.accounts_service.domain.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AccountAuthorizedSignatoryId implements Serializable {
    /**
     * Referencia al accountId
     */
    private Long accountId;
    /**
     * Referencia al customerId que es la firma autorizada
     */
    private Long authorizedSignatoryId;
}
