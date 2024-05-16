package org.nttdata.customers_service.controller.error;

import lombok.extern.log4j.Log4j2;
import org.nttdata.customers_service.domain.exception.NotFoundException;
import org.nttdata.customers_service.domain.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

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
    public Mono<ErrorDto> handleMethodArgumentNotValidException(WebExchangeBindException exception) {
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
        return Mono.just(
                new ErrorDto(
                        "400 - Bad request",
                        exception.getMessage(),
                        null,
                        attributeValidationMap
                ));
    }

    /**
     * Captura los errores cuando no se encuentra el cliente.
     *
     * @param exception Excepción lanzada cuando no se encuentra un cliente.
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorDto> handleClientNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return Mono.just(
                new ErrorDto(
                        "404 - Not found",
                        exception.getMessage(),
                        Arrays.stream(exception.getStackTrace()).map(Objects::toString).collect(Collectors.joining(",")),
                        null
                ));
    }

    /**
     * Captura cualquier error en ejecución no catalogado.
     *
     * @param exception Excepción en ejecución lanzada.
     * @return una instancia reactiva con el detalle del error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorDto> serverExceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return Mono.just(
                new ErrorDto(
                        "500 - Internal server error.",
                        exception.getMessage(),
                        Arrays.stream(exception.getStackTrace()).map(Objects::toString).collect(Collectors.joining(",")),
                        null
                ));
    }
}
