package org.nttdata.accounts_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Es el DTO que muestra los errores con algún formato en específico.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    /**
     * Http status código de error y descripción corta
     */
    private String status;
    /**
     * Mensaje de error
     */
    private String message;
    /**
     * Solo se muestra si no es nulo
     * Obtiene el trace del error
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String details;
    /**
     * Solo se muestra si no es nulo
     * Obtiene el trace del error cuando son validaciones en los DTO que vienen desde el request
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> validationDetails;
}
