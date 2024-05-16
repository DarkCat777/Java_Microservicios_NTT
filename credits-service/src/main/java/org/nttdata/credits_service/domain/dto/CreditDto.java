package org.nttdata.credits_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.credits_service.domain.type.CreditState;
import org.nttdata.credits_service.domain.type.CreditType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * DTO for {@link org.nttdata.credits_service.domain.entity.Credit}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto implements Serializable {
    private String id;
    @NotBlank
    private String creditType;
    @NotEmpty
    private String ownerId;
    @PositiveOrZero
    private Double balance;
    @PositiveOrZero
    private Double outstandingBalance;
    @PositiveOrZero
    private Double interestRate;
    @NotNull
    private OffsetDateTime startDate;
    @NotNull
    private OffsetDateTime dueDate;
    private Set<String> paymentIds;
    @NotBlank
    private String creditState;
}