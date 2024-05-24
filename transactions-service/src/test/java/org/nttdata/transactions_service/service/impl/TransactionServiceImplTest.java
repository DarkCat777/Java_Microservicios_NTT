package org.nttdata.transactions_service.service.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.exception.NotFoundException;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.nttdata.transactions_service.mapper.ITransactionMapper;
import org.nttdata.transactions_service.repository.TransactionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private ITransactionMapper transactionMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private TransactionDto transactionDto;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setFromBankProductId(1L);
        transaction.setBankProductType(BankProductType.CREDIT_CARD);

        transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setFromBankProductId(1L);
        transactionDto.setBankProductType(BankProductType.CREDIT_CARD.name());
    }

    @Test
    void testCreateTransaction() {
        when(transactionMapper.toEntity(any(TransactionDto.class))).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDto);

        TransactionDto result = transactionService.createTransaction(transactionDto);

        assertNotNull(result);
        assertEquals(transactionDto.getId(), result.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testGetTransactionById() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDto);

        TransactionDto result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(transactionDto.getId(), result.getId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            transactionService.getTransactionById(1L);
        });

        assertEquals("La transacción con el id 1 no existe.", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testListTransactionsByBankProductId() {
        when(transactionRepository.findByBankProductTypeAndFromBankProductId(any(BankProductType.class), anyLong()))
                .thenReturn(Collections.singletonList(transaction));
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDto);

        List<TransactionDto> result = transactionService.listTransactionsByBankProductId("CREDIT_CARD", 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transactionDto.getId(), result.get(0).getId());
        verify(transactionRepository, times(1)).findByBankProductTypeAndFromBankProductId(BankProductType.CREDIT_CARD, 1L);
    }

    @Test
    void testCountTransactionByBankProductIdAndCurrentMonth() {
        when(transactionRepository.findByMonthAndBankProductIdAndBankProductType(anyInt(), anyInt(), anyLong(), any(BankProductType.class)))
                .thenReturn(5L);

        Long result = transactionService.countTransactionByBankProductIdAndCurrentMonth("CREDIT_CARD", 1L);

        assertNotNull(result);
        assertEquals(5L, result);
        verify(transactionRepository, times(1)).findByMonthAndBankProductIdAndBankProductType(anyInt(), anyInt(), eq(1L), eq(BankProductType.CREDIT_CARD));
    }

    @Test
    void testDeleteTransactionById() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));
        doNothing().when(transactionRepository).delete(any(Transaction.class));

        transactionService.deleteTransactionById(1L);

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);
    }

    @Test
    void testDeleteTransactionById_NotFound() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.deleteTransactionById(1L));

        assertEquals("La transacción con el id 1 no existe.", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
    }
}