package org.nttdata.credits_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.nttdata.credits_service.domain.exception.MicroserviceError;
import org.nttdata.credits_service.service.feign.CustomerFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerFeignClient customerFeignClient;

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        // TODO: Debug this code fragment
        ResponseEntity<?> response = customerFeignClient.getCustomerById(customerId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return (CustomerDto) response.getBody();
        } else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw new MicroserviceError("customers", (Map<String, String>) response.getBody());
        } else {
            throw new MicroserviceError("customers", (Map<String, String>) response.getBody());
        }
    }
}