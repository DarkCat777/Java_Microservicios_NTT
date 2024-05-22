package org.nttdata.accounts_service.domain.exception;

import lombok.AllArgsConstructor;
import org.nttdata.accounts_service.domain.dto.ErrorDto;

@AllArgsConstructor
public class MicroserviceError extends RuntimeException {
    private final String microservice;
    private final ErrorDto errors;
}
