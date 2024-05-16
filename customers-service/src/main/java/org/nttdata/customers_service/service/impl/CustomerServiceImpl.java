package org.nttdata.customers_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.customers_service.domain.exception.NotFoundException;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.mapper.ICustomerMapper;
import org.nttdata.customers_service.repository.CustomerRepository;
import org.nttdata.customers_service.service.ICustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.nttdata.customers_service.domain.exception.NotFoundException.CUSTOMER_NOT_FOUND_TEMPLATE;

/**
 * Implementación de la interface de clientes
 *
 * @author Erick David Carpio Hachiri
 * @see ICustomerService
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final ICustomerMapper customerMapper;

    @Override
    public Mono<CustomerDto> createCustomer(CustomerDto newCustomer) {
        return customerRepository.save(customerMapper.toEntity(newCustomer)).publishOn(Schedulers.boundedElastic()).map(customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDto> updateCustomer(String customerId, CustomerDto newCustomer) {
        if (newCustomer.getId() == null) {
            return Mono.error(new IllegalArgumentException("Id es nulo."));
        } else {
            return customerRepository.findById(customerId)
                    .switchIfEmpty(Mono.error(new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, newCustomer.getId()))))
                    .flatMap(clientEntity -> {
                        if (newCustomer.getCustomerType().equals(clientEntity.getCustomerType().toString())) {
                            return customerRepository.save(customerMapper.partialUpdate(newCustomer, clientEntity))
                                    .publishOn(Schedulers.boundedElastic())
                                    .map(customerMapper::toDto);
                        } else {
                            return Mono.error(new IllegalArgumentException("El tipo de cliente no debe cambiar."));
                        }
                    });
        }
    }

    @Override
    public Mono<CustomerDto> getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerId))))
                .publishOn(Schedulers.boundedElastic())
                .map(customerMapper::toDto);
    }

    @Override
    public Flux<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .map(customerMapper::toDto);
    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerId))))
                .flatMap(clientEntity -> customerRepository.deleteById(clientEntity.getId()));
    }
}
