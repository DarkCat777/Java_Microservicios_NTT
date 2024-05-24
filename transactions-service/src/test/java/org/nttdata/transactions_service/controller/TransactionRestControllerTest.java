package org.nttdata.transactions_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TransactionRestController.class)
class TransactionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ITransactionService transactionService;

    private TransactionDto transactionDto;

    @BeforeEach
    void setUp() {
        transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setAmount(100.0);
        transactionDto.setFromBankProductId(1L);
        transactionDto.setBankProductType("CREDIT_CARD");
        transactionDto.setTransactionType("PAYMENT");
    }

    @Test
    void testCreateTransaction() throws Exception {
        Mockito.when(transactionService.createTransaction(Mockito.any(TransactionDto.class))).thenReturn(transactionDto);

        mockMvc.perform(post("/api/transactions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fromBankProductId", is(1)))
                .andExpect(jsonPath("$.bankProductType", is("CREDIT_CARD")));
    }

    @Test
    void testGetTransactionById() throws Exception {
        Mockito.when(transactionService.getTransactionById(anyLong())).thenReturn(transactionDto);

        mockMvc.perform(get("/api/transactions/{transactionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fromBankProductId", is(1)))
                .andExpect(jsonPath("$.bankProductType", is("CREDIT_CARD")));
    }

    @Test
    void testListTransactionByBankProductId() throws Exception {
        List<TransactionDto> transactionDtos = Collections.singletonList(transactionDto);
        Mockito.when(transactionService.listTransactionsByBankProductId(anyString(), anyLong())).thenReturn(transactionDtos);

        mockMvc.perform(get("/api/transactions/by-bank-product/{bankProductType}/{bankProductId}", "CREDIT_CARD", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].fromBankProductId", is(1)))
                .andExpect(jsonPath("$[0].bankProductType", is("CREDIT_CARD")));
    }

    @Test
    void testCountTransactionInCurrentMonth() throws Exception {
        Mockito.when(transactionService.countTransactionByBankProductIdAndCurrentMonth(anyString(), anyLong())).thenReturn(5L);

        mockMvc.perform(get("/api/transactions/count-transactions-in-current-month/{bankProductType}/{bankProductId}", "CREDIT_CARD", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testDeleteTransactionById() throws Exception {
        Mockito.doNothing().when(transactionService).deleteTransactionById(anyLong());

        mockMvc.perform(delete("/api/transactions/delete/{transactionId}", 1L))
                .andExpect(status().isNoContent());
    }
}
