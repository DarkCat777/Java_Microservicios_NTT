package org.nttdata.accounts_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.accounts_service.domain.entity.Account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private Long id;
    @NotNull
    private Long ownerId;
    @PositiveOrZero
    private Double balance;
    @NotBlank
    private String accountType;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
    private Set<Long> holderIds;
    private Set<Long> authorizedSignatoryIds;
}