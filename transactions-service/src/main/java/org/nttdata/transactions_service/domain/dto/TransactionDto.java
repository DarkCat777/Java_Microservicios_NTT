package org.nttdata.transactions_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.nttdata.transactions_service.domain.type.TransactionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Transaction}
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