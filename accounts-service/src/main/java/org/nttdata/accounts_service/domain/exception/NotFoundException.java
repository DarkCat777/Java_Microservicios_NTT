package org.nttdata.accounts_service.domain.exception;

/**
 * Excepción que se lanza cuando no existe un cuenta.
 *
 * @author Erick David Carpio Hachiri
 */
public class NotFoundException extends RuntimeException {

    public static final String ACCOUNT_NOT_FOUND_TEMPLATE = "Cuenta con el id '%s' no encontrado";
    public static final String CUSTOMER_NOT_FOUND_TEMPLATE = "Cliente con el id '%s' no encontrado";

    /**
     * Construye una excepción de cuenta no encontrada con el mensaje especificado.
     *
     * @param message El mensaje que describe la excepción.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
