package org.nttdata.accounts_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * DTO for {@link org.nttdata.accounts_service.domain.entity.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private String id;
    @NotBlank
    private String ownerId;
    @PositiveOrZero
    private Double balance;
    @NotBlank
    private String accountType;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private Set<String> holderIds;
    private Set<String> authorizedSignatoryIds;
}