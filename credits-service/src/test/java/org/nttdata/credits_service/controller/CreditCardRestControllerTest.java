package org.nttdata.credits_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.nttdata.credits_service.domain.dto.CreditCardDto;
import org.nttdata.credits_service.service.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreditCardRestController.class)
class CreditCardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICreditCardService creditCardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCreditCard() throws Exception {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setOwnerId(1L);
        creditCardDto.setOutstandingBalance(500.0);

        Mockito.when(creditCardService.createCreditCard(any(CreditCardDto.class))).thenReturn(creditCardDto);

        mockMvc.perform(post("/api/credit-products/credit-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCardDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetCreditCardById() throws Exception {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setOwnerId(1L);
        creditCardDto.setOutstandingBalance(500.0);

        Mockito.when(creditCardService.getCreditCardById(anyLong())).thenReturn(creditCardDto);

        mockMvc.perform(get("/api/credit-products/credit-cards/{creditCardId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testPaymentCreditCard() throws Exception {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setOwnerId(1L);
        creditCardDto.setOutstandingBalance(400.0);

        Mockito.when(creditCardService.paymentCreditCard(anyLong(), anyDouble())).thenReturn(creditCardDto);

        mockMvc.perform(put("/api/credit-products/credit-cards/{creditCardId}/credit-card-payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(100.0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.outstandingBalance").value(400.0));
    }

    @Test
    void testChargeToCreditCard() throws Exception {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setOwnerId(1L);
        creditCardDto.setOutstandingBalance(600.0);

        Mockito.when(creditCardService.chargeToCreditCard(anyLong(), anyDouble())).thenReturn(creditCardDto);

        mockMvc.perform(put("/api/credit-products/credit-cards/{creditCardId}/charge-to-credit-card", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(100.0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.outstandingBalance").value(600.0));
    }

    @Test
    void testUpdateCreditCard() throws Exception {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(1L);
        creditCardDto.setOwnerId(1L);
        creditCardDto.setOutstandingBalance(400.0);

        Mockito.when(creditCardService.updateCreditCardById(anyLong(), any(CreditCardDto.class))).thenReturn(creditCardDto);

        mockMvc.perform(put("/api/credit-products/credit-cards/{creditCardId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditCardDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteCreditCard() throws Exception {
        Mockito.doNothing().when(creditCardService).deleteCreditCardById(anyLong());

        mockMvc.perform(delete("/api/credit-products/credit-cards/{creditCardId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}