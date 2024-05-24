package org.nttdata.credits_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.credits_service.domain.dto.CreditCardDto;
import org.nttdata.credits_service.domain.dto.TransactionDto;
import org.nttdata.credits_service.domain.entity.CreditCard;
import org.nttdata.credits_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.credits_service.domain.exception.NotFoundException;
import org.nttdata.credits_service.mapper.ICreditCardMapper;
import org.nttdata.credits_service.repository.CreditCardRepository;
import org.nttdata.credits_service.service.feign.TransactionFeignClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private TransactionFeignClient transactionService;

    @Spy
    private ICreditCardMapper creditCardMapper = Mappers.getMapper(ICreditCardMapper.class);

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    private CreditCardDto creditCardDto;
    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setBalance(1000.0);
        creditCardDto.setOutstandingBalance(200.0);

        creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setBalance(1000.0);
        creditCard.setOutstandingBalance(200.0);
    }

    @Test
    void testCreateCreditCard() {
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);

        CreditCardDto result = creditCardService.createCreditCard(creditCardDto);

        assertEquals(creditCardDto, result);
        verify(creditCardRepository).save(creditCard);
        verify(creditCardMapper).toEntity(creditCardDto);
        verify(creditCardMapper).toDto(creditCard);
    }

    @Test
    void testGetCreditCardById() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(creditCardMapper.toDto(creditCard)).thenReturn(creditCardDto);

        CreditCardDto result = creditCardService.getCreditCardById(1L);

        assertEquals(creditCardDto, result);
        verify(creditCardRepository).findById(1L);
        verify(creditCardMapper).toDto(creditCard);
    }

    @Test
    void testGetCreditCardById_NotFound() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> creditCardService.getCreditCardById(1L));

        assertEquals("Tarjeta de crédito con el id '1' no encontrado", exception.getMessage());
    }

    @Test
    void testUpdateCreditCardById() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);

        CreditCardDto result = creditCardService.updateCreditCardById(1L, creditCardDto);

        assertEquals(creditCardDto, result);
        verify(creditCardRepository).findById(1L);
        verify(creditCardRepository).save(creditCard);
    }

    @Test
    void testPaymentCreditCard() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        doAnswer(invocation -> invocation.getArgument(0)).when(creditCardRepository).save(any(CreditCard.class));

        CreditCardDto result = creditCardService.paymentCreditCard(1L, 100.0);

        assertEquals(creditCardDto.getOutstandingBalance() - 100, result.getOutstandingBalance());
        verify(creditCardRepository).findById(1L);
        verify(transactionService).createTransaction(any(TransactionDto.class));
        verify(creditCardRepository).save(creditCard);
    }

    @Test
    void testPaymentCreditCard_InvalidAmount() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> creditCardService.paymentCreditCard(1L, 300.0));

        assertEquals("Invalid payment amount", exception.getMessage());
    }

    @Test
    void testChargeToCreditCard() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));
        doAnswer(invocation -> invocation.getArgument(0)).when(creditCardRepository).save(any(CreditCard.class));

        CreditCardDto result = creditCardService.chargeToCreditCard(1L, 100.0);

        assertEquals(creditCardDto.getOutstandingBalance() + 100.0, result.getOutstandingBalance());
        verify(creditCardRepository).findById(1L);
        verify(transactionService).createTransaction(any(TransactionDto.class));
        verify(creditCardRepository).save(creditCard);
    }

    @Test
    void testChargeToCreditCard_InvalidAmount() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

        BusinessLogicValidationException exception = assertThrows(BusinessLogicValidationException.class, () -> {
            creditCardService.chargeToCreditCard(1L, 900.0);
        });

        assertEquals("Invalid charge amount", exception.getMessage());
    }

    @Test
    void testDeleteCreditCardById() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(creditCard));

        creditCardService.deleteCreditCardById(1L);

        verify(creditCardRepository).findById(1L);
        verify(creditCardRepository).delete(creditCard);
    }

    @Test
    void testDeleteCreditCardById_NotFound() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> creditCardService.deleteCreditCardById(1L));

        assertEquals("Tarjeta de crédito con el id '1' no encontrado", exception.getMessage());
    }
}
