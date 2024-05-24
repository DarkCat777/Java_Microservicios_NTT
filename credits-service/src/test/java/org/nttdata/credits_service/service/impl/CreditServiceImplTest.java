package org.nttdata.credits_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.nttdata.credits_service.domain.entity.Credit;
import org.nttdata.credits_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.credits_service.domain.exception.NotFoundException;
import org.nttdata.credits_service.domain.type.CustomerType;
import org.nttdata.credits_service.mapper.ICreditMapper;
import org.nttdata.credits_service.repository.CreditRepository;
import org.nttdata.credits_service.service.feign.CustomerFeignClient;
import org.nttdata.credits_service.service.feign.TransactionFeignClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerFeignClient customerService;

    @Mock
    private TransactionFeignClient transactionService;

    @Spy
    private ICreditMapper creditMapper = Mappers.getMapper(ICreditMapper.class);

    @InjectMocks
    private CreditServiceImpl creditService;

    private CreditDto creditDto;
    private Credit credit;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setOwnerId(1L);
        creditDto.setOutstandingBalance(500.0);

        credit = new Credit();
        credit.setId(1L);
        credit.setOwnerId(1L);
        credit.setOutstandingBalance(500.0);

        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setCustomerType(CustomerType.PERSONAL.name());
    }

    @Test
    void testCreateCredit() {
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDto);
        when(creditRepository.existsCreditsByOwnerId(anyLong())).thenReturn(false);
        when(creditMapper.toEntity(any(CreditDto.class))).thenReturn(credit);
        when(creditRepository.save(any(Credit.class))).thenReturn(credit);

        CreditDto result = creditService.createCredit(creditDto);
        assertNotNull(result);
        assertEquals(creditDto.getId(), result.getId());
    }

    @Test
    void testCreateCreditForPersonalCustomerWithExistingCredit() {
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDto);
        when(creditRepository.existsCreditsByOwnerId(anyLong())).thenReturn(true);

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> {
            creditService.createCredit(creditDto);
        });

        assertEquals("Business logic invalid", exception.getMessage());
    }

    @Test
    void testUpdateCreditById() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.of(credit));
        when(creditRepository.save(any(Credit.class))).thenReturn(credit);

        CreditDto result = creditService.updateCreditById(1L, creditDto);
        assertNotNull(result);
        assertEquals(creditDto.getId(), result.getId());
    }

    @Test
    void testGetCreditById() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.of(credit));

        CreditDto result = creditService.getCreditById(1L);
        assertNotNull(result);
        assertEquals(creditDto.getId(), result.getId());
    }

    @Test
    void testGetCreditByIdNotFound() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            creditService.getCreditById(1L);
        });

        assertEquals("Crédito con el id '1' no encontrado", exception.getMessage());
    }

    @Test
    void testDeleteCreditById() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.of(credit));
        doNothing().when(creditRepository).delete(any(Credit.class));

        assertDoesNotThrow(() -> creditService.deleteCreditById(1L));
    }

    @Test
    void testPaymentCredit() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.of(credit));
        doAnswer(invocation -> invocation.getArgument(0)).when(creditRepository).save(any(Credit.class));

        CreditDto result = creditService.paymentCredit(1L, 100.0);
        assertNotNull(result);
        assertEquals(400.0, result.getOutstandingBalance());
    }

    @Test
    void testPaymentCreditExceedsOutstandingBalance() {
        when(creditRepository.findById(anyLong())).thenReturn(Optional.of(credit));

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> {
            creditService.paymentCredit(1L, 600.0);
        });

        assertEquals("Payment Amount", exception.getMessage());
    }
}