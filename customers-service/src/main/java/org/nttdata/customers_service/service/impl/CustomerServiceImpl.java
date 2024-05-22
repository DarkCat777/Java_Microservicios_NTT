package org.nttdata.customers_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.customers_service.domain.entity.Customer;
import org.nttdata.customers_service.domain.exception.NotFoundException;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.mapper.ICustomerMapper;
import org.nttdata.customers_service.repository.CustomerRepository;
import org.nttdata.customers_service.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional
    public CustomerDto createCustomer(CustomerDto newCustomer) {
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(newCustomer)));
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Long customerId, CustomerDto newCustomer) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerId)));
        if (newCustomer.getCustomerType().equals(customer.getCustomerType().toString())) {
            return customerMapper.toDto(customerRepository.save(customerMapper.partialUpdate(newCustomer, customer)));
        } else {
            throw new IllegalArgumentException("El tipo de cliente no debe cambiar.");
        }

    }

    @Override
    @Transactional
    public CustomerDto getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerId)));
        customerRepository.delete(customer);
    }

    @Override
    public Boolean existsAllByCustomerIds(Set<Long> customerIds) {
        return customerRepository.existsAllByIdIn(customerIds);
    }
}
