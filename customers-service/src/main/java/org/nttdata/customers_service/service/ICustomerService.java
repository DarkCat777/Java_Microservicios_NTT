package org.nttdata.customers_service.service;

import org.nttdata.customers_service.domain.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface de operaciones del para el servicio de cliente (CRUD y lectura de todos los clientes)
 * @see CustomerDto
 * @see Mono
 * @see Flux
 * @author Erick David Carpio Hachiri
 */
public interface ICustomerService {

    /**
     * Crea un nuevo cliente.
     *
     * @param newCustomer La información del cliente a crear.
     * @return Un Mono que emite el DTO del cliente actualizado o creado.
     */
    Mono<CustomerDto> createCustomer(CustomerDto newCustomer);

    /**
     * Actualiza un cliente existente.
     *
     * @param customerId El ID del cliente a actualizar.
     * @param newCustomer La información actualizada del cliente.
     * @return Un Mono que emite el DTO del cliente actualizado.
     */
    Mono<CustomerDto> updateCustomer(String customerId, CustomerDto newCustomer);

    /**
     * Obtiene un cliente por su ID.
     *
     * @param customerId El ID del cliente a obtener.
     * @return Un Mono que emite el DTO del cliente encontrado.
     */
    Mono<CustomerDto> getCustomer(String customerId);

    /**
     * Obtiene todos los clientes.
     *
     * @return Un Flux que emite todos los DTO de clientes.
     */
    Flux<CustomerDto> listCustomers();

    /**
     * Elimina un cliente por su ID.
     *
     * @param customerId El ID del cliente a eliminar.
     * @return Un Mono que indica la finalización de la operación de eliminación.
     */
    Mono<Void> deleteCustomer(String customerId);
}
