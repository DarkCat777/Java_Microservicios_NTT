package org.nttdata.transactions_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.nttdata.transactions_service.domain.type.TransactionType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que describe las transacciones que se realizan sobre un producto bancario
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    /**
     * Es el identificador único
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Puede ser positivo o negativo pero no 0.
     * Cantidad o monto de la transacción.
     */
    @Column(nullable = false)
    private Double amount;
    /**
     * Indica el tipo de transacción que se realiza.
     */
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    /**
     * Indica el tipo de producto al que corresponde la transacción.
     */
    @Enumerated(EnumType.STRING)
    private BankProductType bankProductType;

    /**
     * Hace referencia al producto al que sufre estos cambios en su monto.
     */
    @Column(nullable = false)
    private Long fromBankProductId;

    /**
     * Hace referencia al producto al que sufre estos cambios en su monto.
     * Puede ser nulo
     */
    private Long toBankProductId;

    /**
     * Fecha de la ejecución de la transacción
     */
    @CreatedDate
    private LocalDate transactionDate;
}
