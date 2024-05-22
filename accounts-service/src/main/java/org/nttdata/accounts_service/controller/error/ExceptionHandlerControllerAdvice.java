package org.nttdata.accounts_service.controller.error;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.nttdata.accounts_service.domain.dto.ErrorDto;
import org.nttdata.accounts_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.accounts_service.domain.exception.MicroserviceError;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /**
     * Captura los errores relacionados a las validaciones de datos en los DTO o métodos
     *
     * @param exception ambos tipos de excepción tiene la función getBindingResult()
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        Map<String, String> attributeValidationMap = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error.getDefaultMessage() != null)
                .collect(Collectors.toMap(error -> {
                    try {
                        return ((FieldError) error).getField();
                    } catch (ClassCastException ex) {
                        return error.getObjectName();
                    }
                }, DefaultMessageSourceResolvable::getDefaultMessage));
        return new ErrorDto(
                "400 - Bad request",
                exception.getMessage(),
                null,
                attributeValidationMap
        );
    }

    /**
     * Captura los errores cuando no se encuentra el cliente.
     *
     * @param exception Excepción lanzada cuando no se encuentra un cliente.
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler(BusinessLogicValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBusinessLogicValidationException(BusinessLogicValidationException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorDto(
                "400 - Bad request",
                exception.getMessage(),
                null,
                exception.getValidations()
        );
    }

    /**
     * Captura los errores cuando no se encuentra el cliente.
     *
     * @param exception Excepción lanzada cuando no se encuentra un cliente.
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleAccountNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorDto(
                "404 - Not found",
                exception.getMessage(),
                null,
                null
        );
    }

    /**
     * Captura cualquier error en ejecución no catalogado.
     *
     * @param exception Excepción en ejecución lanzada.
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto serverExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorDto(
                "500 - Internal server error.",
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(Objects::toString).collect(Collectors.joining(",")),
                null
        );
    }
}
