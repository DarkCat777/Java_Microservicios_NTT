package org.nttdata.customers_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nttdata.customers_service.domain.dto.CustomerDto;
import org.nttdata.customers_service.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

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

    private final ICustomerService customerService;

    /**
     * Obtiene todos los clientes.
     *
     * @return Una lista de objetos CustomerDto que representa todos los clientes.
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getCustomers() {
        return customerService.listCustomers();
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param customerId El ID del cliente.
     * @return El objeto CustomerDto que representa el cliente encontrado.
     */
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerById(@PathVariable @PositiveOrZero Long customerId) {
        return customerService.getCustomer(customerId);
    }

    /**
     * Verifica si todos los clientes con los IDs especificados existen.
     *
     * @param customerIds Un conjunto de IDs de cliente.
     * @return true si todos los clientes con los IDs especificados existen, false de lo contrario.
     */
    @GetMapping("/exists-all-by-ids")
    @ResponseStatus(HttpStatus.OK)
    public Boolean existAllCustomerByIdsIn(@RequestBody @NotEmpty Set<Long> customerIds) {
        return customerService.existsAllByCustomerIds(customerIds);
    }

    /**
     * Crea un nuevo cliente.
     *
     * @param customerDto El objeto CustomerDto que representa el nuevo cliente.
     * @return El objeto CustomerDto que representa el cliente creado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Validated @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    /**
     * Actualiza un cliente existente.
     *
     * @param customerId  El ID del cliente a actualizar.
     * @param customerDto El objeto CustomerDto que representa los nuevos datos del cliente.
     * @return El objeto CustomerDto que representa el cliente actualizado.
     */
    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto updateCustomer(
            @PathVariable @PositiveOrZero Long customerId,
            @RequestBody @Validated CustomerDto customerDto
    ) {
        return customerService.updateCustomer(customerId, customerDto);
    }

    /**
     * Elimina un cliente existente.
     *
     * @param customerId El ID del cliente a eliminar.
     */
    @DeleteMapping("/delete/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable @PositiveOrZero Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}

