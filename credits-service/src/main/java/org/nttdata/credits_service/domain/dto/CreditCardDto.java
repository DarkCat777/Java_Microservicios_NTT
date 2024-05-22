package org.nttdata.credits_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.credits_service.domain.entity.CreditCard;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link CreditCard}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto implements Serializable {
    private Long id;
    @Positive
    private Long ownerId;
    @PositiveOrZero
    private Double balance;
    @PositiveOrZero
    private Double outstandingBalance;
    @PositiveOrZero
    private Double interestRate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastModifiedDate;
}