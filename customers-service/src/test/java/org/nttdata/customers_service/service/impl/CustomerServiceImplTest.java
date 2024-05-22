package org.nttdata.customers_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.customers_service.domain.entity.Customer;
import org.nttdata.customers_service.domain.exception.NotFoundException;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.domain.type.CustomerType;
import org.nttdata.customers_service.mapper.ICustomerMapper;
import org.nttdata.customers_service.repository.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.nttdata.customers_service.domain.exception.NotFoundException.CUSTOMER_NOT_FOUND_TEMPLATE;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ICustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setCustomerType(CustomerType.PERSONAL);

        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setCustomerType("PERSONAL");
    }

    @Test
    void testCreateCustomer() {
        when(customerMapper.toEntity(any(CustomerDto.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        CustomerDto result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
    }

    @Test
    void testUpdateCustomer_Success() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerMapper.partialUpdate(any(CustomerDto.class), any(Customer.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        CustomerDto result = customerService.updateCustomer(1L, customerDto);

        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
    }

    @Test
    void testUpdateCustomer_NotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.updateCustomer(customerDto.getId(), customerDto));

        assertTrue(exception.getMessage().contains(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerDto.getId())));
    }

    @Test
    void testUpdateCustomer_InvalidType() {
        customerDto.setCustomerType("VIP");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(1L, customerDto));

        assertEquals("El tipo de cliente no debe cambiar.", exception.getMessage());
    }

    @Test
    void testGetCustomer_Success() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomer(1L);

        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
    }

    @Test
    void testGetCustomer_NotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.getCustomer(customerDto.getId()));

        assertTrue(exception.getMessage().contains(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerDto.getId())));
    }

    @Test
    void testListCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        when(customerMapper.toDto(any(Customer.class))).thenReturn(customerDto);

        List<CustomerDto> result = customerService.listCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customerDto.getId(), result.get(0).getId());
    }

    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        doNothing().when(customerRepository).delete(any(Customer.class));

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.deleteCustomer(customerDto.getId()));

        assertTrue(exception.getMessage().contains(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, customerDto.getId())));
    }

    @Test
    void testExistsAllByCustomerIds() {
        when(customerRepository.existsAllByIdIn(anySet())).thenReturn(true);

        Boolean result = customerService.existsAllByCustomerIds(Set.of(1L, 2L));

        assertTrue(result);
    }
}