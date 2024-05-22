package org.nttdata.customers_service.service;

import org.nttdata.customers_service.domain.dto.CustomerDto;

import java.util.List;
import java.util.Set;

/**
 * Interface de operaciones del para el servicio de cliente (CRUD y lectura de todos los clientes)
 *
 * @see CustomerDto
 * @author Erick David Carpio Hachiri
 */
public interface ICustomerService {

    /**
     * Crea un nuevo cliente.
     *
     * @param newCustomer El objeto CustomerDto que contiene la información del nuevo cliente.
     * @return El objeto CustomerDto que representa el cliente creado.
     */
    CustomerDto createCustomer(CustomerDto newCustomer);

    /**
     * Actualiza un cliente existente.
     *
     * @param customerId  El ID del cliente que se desea actualizar.
     * @param newCustomer El objeto CustomerDto que contiene los nuevos datos del cliente.
     * @return El objeto CustomerDto que representa el cliente actualizado.
     */
    CustomerDto updateCustomer(Long customerId, CustomerDto newCustomer);

    /**
     * Obtiene un cliente por su ID.
     *
     * @param customerId El ID del cliente que se desea obtener.
     * @return El objeto CustomerDto que representa el cliente encontrado.
     */
    CustomerDto getCustomer(Long customerId);

    /**
     * Obtiene todos los clientes.
     *
     * @return Una lista de objetos CustomerDto que representan todos los clientes.
     */
    List<CustomerDto> listCustomers();

    /**
     * Elimina un cliente por su ID.
     *
     * @param customerId El ID del cliente que se desea eliminar.
     */
    void deleteCustomer(Long customerId);

    /**
     * Verifica si todos los clientes con los IDs especificados existen.
     *
     * @param customerIds Un conjunto de IDs de cliente.
     * @return true si todos los clientes con los IDs especificados existen, false de lo contrario.
     */
    Boolean existsAllByCustomerIds(Set<Long> customerIds);
}
