package org.nttdata.customers_service.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

/**
 * Endpoints de los clientes del banco
 *
 * @author Erick David Carpio Hachiri
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerRestController {
    /**
     * Referencia al interface {@link ICustomerService}
     */
    private final ICustomerService clientService;

    /**
     * Endpoint que retorna todos los clientes.
     *
     * @return retorna una instancia de reactiva de multiples elementos(clientes)
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDto> getCustomers() {
        log.info("GET - ALL CUSTOMERS");
        return clientService.listCustomers();
    }

    /**
     * Endpoint que retorna un cliente por su id.
     *
     * @return retorna una instancia de reactiva de un elemento(cliente)
     */
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> getCustomerById(@PathVariable @NotBlank String customerId) {
        log.info(String.format("GET - CUSTOMER BY ID %s", customerId));
        return clientService.getCustomer(customerId);
    }

    /**
     * Endpoint que retorna el cliente creado.
     *
     * @return retorna una instancia de reactiva de un elemento(cliente)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> createCustomer(@Validated @RequestBody CustomerDto customerDto) {
        log.info(String.format("POST - CREATE CUSTOMER BY %s", customerDto.toString()));
        return clientService.createCustomer(customerDto);
    }

    /**
     * Endpoint que retorna el cliente actualizado por su id.
     *
     * @return retorna una instancia de reactiva de un elemento(cliente)
     */
    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> updateCustomer(
            @PathVariable @NotBlank String customerId,
            @RequestBody @Validated CustomerDto customerDto
    ) {
        log.info(String.format("PUT - UPDATE CUSTOMER BY ID %s WITH %s", customerId, customerDto.toString()));
        return clientService.updateCustomer(customerId, customerDto);
    }

    /**
     * Endpoint que elimina un cliente por su id.
     *
     * @return retorna una instancia de reactiva de un elemento(vacío)
     */
    @DeleteMapping("/delete/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable @NotBlank String customerId) {
        log.info(String.format("DELETE - DELETE CUSTOMER BY ID'S %s", customerId));
        return clientService.deleteCustomer(customerId);
    }
}
