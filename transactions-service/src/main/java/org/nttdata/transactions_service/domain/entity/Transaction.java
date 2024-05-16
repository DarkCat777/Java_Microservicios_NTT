package org.nttdata.transactions_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.transactions_service.domain.type.TransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que describe las transacciones que se realizan sobre un producto bancario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("transactions")
public class Transaction {
    /**
     * Es el identificador único
     */
    @Id
    private String id;
    /**
     * Puede ser positivo o negativo pero no 0.
     * Cantidad o monto de la transacción.
     */
    private Long amount;
    /**
     * Indica el tipo de transacción que se realiza.
     */
    private TransactionType transactionType;
    /**
     * Hace referencia al producto al que sufre estos cambios en su monto.
     */
    private String bankProductId;
}
