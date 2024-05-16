package org.nttdata.transactions_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.transactions_service.domain.dto.operation.Create;
import org.nttdata.transactions_service.domain.dto.operation.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link org.nttdata.transactions_service.domain.entity.Transaction}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {
    @Null(groups = { Create.class })
    @NotNull(groups = { Update.class })
    private String id;
    @NotNull
    @Positive
    private Long amount;
    @NotNull
    private String transactionType;
    @NotBlank
    private String productId;
}