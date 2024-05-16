package org.nttdata.transactions_service.domain.type;

/**
 * Tipo enumerado que describe los tipos de transacciones que puede realizar una cuenta bancaria.
 *
 * @author Erick David Carpio Hachiri
 */
public enum TransactionType {
    /**
     * Tipo de transacción de depósito "cuando ingresa dinero en algún producto bancario"
     */
    DEPOSIT,
    /**
     * Tipo de transacción de retiro "cuando se retira dinero de algún producto financiero"
     */
    WITHDRAWAL
}
