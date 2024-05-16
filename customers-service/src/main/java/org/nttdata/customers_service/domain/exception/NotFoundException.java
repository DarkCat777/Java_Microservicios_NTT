package org.nttdata.customers_service.domain.exception;

/**
 * Excepción cuando no se encuentra el cliente respectivo en la base de datos
 *
 * @author Erick David Carpio Hachiri
 */
public class NotFoundException extends RuntimeException {

    public static final String CUSTOMER_NOT_FOUND_TEMPLATE = "Cliente con el id '%s' no encontrado";

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
