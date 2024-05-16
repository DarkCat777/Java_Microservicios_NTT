package org.nttdata.transactions_service.domain.exception;

/**
 * Excepción lanzada cuando el microservicio bank product no responde o devuelve un error.
 */
public class BankProductServiceDownException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BankProductServiceDownException(String message) {
        super(message);
    }
}
