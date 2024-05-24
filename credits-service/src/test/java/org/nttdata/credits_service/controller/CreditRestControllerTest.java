package org.nttdata.credits_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.service.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreditRestController.class)
class CreditRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICreditService creditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCredit() throws Exception {
        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setOwnerId(1L);
        creditDto.setOutstandingBalance(500.0);

        Mockito.when(creditService.createCredit(any(CreditDto.class))).thenReturn(creditDto);

        mockMvc.perform(post("/api/credit-products/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetCreditById() throws Exception {
        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setOwnerId(1L);
        creditDto.setOutstandingBalance(500.0);

        Mockito.when(creditService.getCreditById(anyLong())).thenReturn(creditDto);

        mockMvc.perform(get("/api/credit-products/credits/{creditId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testPaymentCredit() throws Exception {
        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setOwnerId(1L);
        creditDto.setOutstandingBalance(400.0);

        Mockito.when(creditService.paymentCredit(anyLong(), any(Double.class))).thenReturn(creditDto);

        mockMvc.perform(put("/api/credit-products/credits/{creditId}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(100.0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.outstandingBalance").value(400.0));
    }

    @Test
    void testUpdateCredit() throws Exception {
        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setOwnerId(1L);
        creditDto.setOutstandingBalance(400.0);

        Mockito.when(creditService.updateCreditById(anyLong(), any(CreditDto.class))).thenReturn(creditDto);

        mockMvc.perform(put("/api/credit-products/credits/{creditId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteCredit() throws Exception {
        Mockito.doNothing().when(creditService).deleteCreditById(anyLong());

        mockMvc.perform(delete("/api/credit-products/credits/{creditId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}