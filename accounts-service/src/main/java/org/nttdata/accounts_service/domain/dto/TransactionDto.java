package org.nttdata.accounts_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.nttdata.transactions_service.domain.entity.Transaction}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {
    private Long id;
    @Positive
    private Double amount;
    @NotBlank
    private String transactionType;
    @NotBlank
    private String bankProductType;
    @Positive
    private Long fromBankProductId;
    private Long toBankProductId; // Positive or Null
    private LocalDate transactionDate;
}