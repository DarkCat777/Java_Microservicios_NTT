package org.nttdata.transactions_service.domain.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessLogicValidationException extends RuntimeException {
    private final Map<String, String> validations;

    public BusinessLogicValidationException(String message, Map<String, String> validations) {
        super(message);
        this.validations = validations;
    }
}
