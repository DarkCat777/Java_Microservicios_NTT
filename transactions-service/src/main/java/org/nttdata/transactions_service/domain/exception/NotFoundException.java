package org.nttdata.transactions_service.domain.exception;

/**
 * Excepción lanzada cuando no existe un producto en la base de datos.
 */
public class NotFoundException extends RuntimeException {

    public static final String CUSTOMER_NOT_FOUND_TEMPLATE = "El cliente con el id %s no existe.";
    public static final String TRANSACTION_NOT_FOUND_TEMPLATE = "La transacción con el id %s no existe.";
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
