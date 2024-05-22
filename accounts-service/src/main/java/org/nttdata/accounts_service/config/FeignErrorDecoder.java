package org.nttdata.accounts_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nttdata.accounts_service.domain.dto.ErrorDto;
import org.nttdata.accounts_service.domain.exception.MicroserviceError;
import org.nttdata.accounts_service.domain.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    private String getMicroserviceName(String path) {
        if (path.contains("customers-service")) {
            return "customer";
        } else if (path.contains("transactions-service")) {
            return "transaction";
        } else {
            return "unknown";
        }
    }

    @Override
    @SneakyThrows
    public Exception decode(String methodKey, Response response) {
        ErrorDto errorDto = objectMapper.readValue(response.body().asInputStream(), ErrorDto.class);
        String microserviceName = this.getMicroserviceName(response.request().url());
        switch (response.status()) {
            case 400:
                return new MicroserviceError(microserviceName, errorDto);
            case 404:
                return new NotFoundException(errorDto.getMessage());
            case 503:
                return new MicroserviceError(microserviceName, new ErrorDto("503 - Service Unavailable", microserviceName + " service API is unavailable", null, null));
            default:
                return new Exception("Exception while getting customer details");
        }
    }
}
