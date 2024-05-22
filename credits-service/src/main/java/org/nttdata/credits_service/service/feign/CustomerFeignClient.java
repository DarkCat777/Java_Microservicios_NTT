package org.nttdata.credits_service.service.feign;

import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(name = "customers-service", path = "/api/customers")
public interface CustomerFeignClient {
    /**
     * Obtiene todos los clientes.
     *
     * @return Una lista de objetos CustomerDto que representa todos los clientes.
     */
    @GetMapping("/all")
    List<CustomerDto> getCustomers();

    /**
     * Obtiene un cliente por su ID.
     *
     * @param customerId El ID del cliente.
     * @return El objeto CustomerDto que representa el cliente encontrado.
     */
    @GetMapping("/{customerId}")
    CustomerDto getCustomerById(@PathVariable Long customerId);

    /**
     * Verifica si todos los clientes con los IDs especificados existen.
     *
     * @param customerIds Un conjunto de IDs de cliente.
     * @return true si todos los clientes con los IDs especificados existen, false de lo contrario.
     */
    @GetMapping("/exists-all-by-ids")
    Boolean existAllCustomerByIdsIn(@RequestBody Set<Long> customerIds);

    /**
     * Crea un nuevo cliente.
     *
     * @param customerDto El objeto CustomerDto que representa el nuevo cliente.
     * @return El objeto CustomerDto que representa el cliente creado.
     */
    @PostMapping
    CustomerDto createCustomer(@RequestBody CustomerDto customerDto);

    /**
     * Actualiza un cliente existente.
     *
     * @param customerId  El ID del cliente a actualizar.
     * @param customerDto El objeto CustomerDto que representa los nuevos datos del cliente.
     * @return El objeto CustomerDto que representa el cliente actualizado.
     */
    @PutMapping("/{customerId}")
    CustomerDto updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto);

    /**
     * Elimina un cliente existente.
     *
     * @param customerId El ID del cliente a eliminar.
     */
    @DeleteMapping("/delete/{customerId}")
    Void deleteCustomer(@PathVariable Long customerId);

}
