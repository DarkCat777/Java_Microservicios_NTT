package org.nttdata.customers_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICustomerService customerService;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setSurnames("Mock Customer");
        customerDto.setLastnames("Customer TestMock");
        customerDto.setCustomerType("PERSONAL");
    }

    @Test
    void testGetCustomers() throws Exception {
        when(customerService.listCustomers()).thenReturn(Collections.singletonList(customerDto));

        mockMvc.perform(get("/api/customers/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomer(anyLong())).thenReturn(customerDto);

        mockMvc.perform(get("/api/customers/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testExistAllCustomerByIdsIn() throws Exception {
        when(customerService.existsAllByCustomerIds(anySet())).thenReturn(true);

        mockMvc.perform(get("/api/customers/exists-all-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2, 3]"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerType", is("PERSONAL")));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(anyLong(), any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(put("/api/customers/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerType", is("PERSONAL")));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/delete/{customerId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
