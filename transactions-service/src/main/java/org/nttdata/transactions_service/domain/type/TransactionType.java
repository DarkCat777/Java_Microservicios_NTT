package org.nttdata.transactions_service.domain.type;

/**
 * Tipo enumerado que describe los tipos de transacciones que puede realizar una cuenta bancaria.
 *
 * @author Erick David Carpio Hachiri
 */
public enum TransactionType {
    /**
     * Tipo de transacción de depósito, aplica a cuentas bancarias
     */
    DEPOSIT,
    /**
     * Tipo de transacción de retiro, aplica a cuentas bancarias
     */
    WITHDRAWAL,
    /**
     * Tipo de transacción de pago en cuotas, pago de créditos
     */
    PAYMENT_IN_INSTALLMENTS,
    /**
     * Tipo de transacción pago, aplica a tarjeta de crédito
     */
    PAYMENT,
    /**
     * Tipo de transacción pago con una tarjeta de crédito
     */
    CHARGE
}
