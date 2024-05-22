package org.nttdata.credits_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link org.nttdata.credits_service.domain.entity.Credit}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto implements Serializable {
    private String id;
    @Positive
    private Long ownerId;
    @Positive
    private Double balance;
    @PositiveOrZero
    private Double outstandingBalance;
    @PositiveOrZero
    private Double interestRate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date startDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dueDate;
    @NotBlank
    private String creditState;
}