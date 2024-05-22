package org.nttdata.credits_service.domain.exception;

import lombok.AllArgsConstructor;
import org.nttdata.credits_service.domain.dto.ErrorDto;

import java.util.Map;

@AllArgsConstructor
public class MicroserviceError extends RuntimeException {
    private final String microservice;
    private final ErrorDto errors;
}
