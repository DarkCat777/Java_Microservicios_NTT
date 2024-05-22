package org.nttdata.accounts_service.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;
import org.nttdata.accounts_service.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountRestController.class)
class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        accountDto = new AccountDto();
        accountDto.setId(1L);
        accountDto.setOwnerId(1L);
        accountDto.setAccountType(AccountTypeEnum.CURRENT.toString());
    }

    @Test
    void testGetAccountById() throws Exception {
        when(accountService.getAccountById(anyLong())).thenReturn(accountDto);

        mockMvc.perform(get("/api/account-products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    void testCreateAccount() throws Exception {
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);

        mockMvc.perform(post("/api/account-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).createAccount(any(AccountDto.class));
    }

    @Test
    void testUpdateAccountById() throws Exception {
        when(accountService.updateAccountById(anyLong(), any(AccountDto.class))).thenReturn(accountDto);

        mockMvc.perform(put("/api/account-products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).updateAccountById(anyLong(), any(AccountDto.class));
    }

    @Test
    void testDepositAccountById() throws Exception {
        when(accountService.depositAccountById(anyLong(), anyDouble())).thenReturn(accountDto);

        mockMvc.perform(put("/api/account-products/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(100.0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).depositAccountById(anyLong(), anyDouble());
    }

    @Test
    void testWithdrawalAccountById() throws Exception {
        when(accountService.withdrawalAccountById(anyLong(), anyDouble())).thenReturn(accountDto);

        mockMvc.perform(put("/api/account-products/1/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(50.0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(accountService, times(1)).withdrawalAccountById(anyLong(), anyDouble());
    }

    @Test
    void testDeleteAccountById() throws Exception {
        doNothing().when(accountService).deleteAccountById(anyLong());

        mockMvc.perform(delete("/api/account-products/1"))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).deleteAccountById(anyLong());
    }
}
