package org.nttdata.accounts_service.service;

import org.nttdata.accounts_service.domain.dto.AccountDto;

import java.util.List;

/**
 * Interface de operaciones del para el servicio de cuentas bancarias (CRUD y lectura de todos los clientes)
 *
 * @see AccountDto
 * @author Erick David Carpio Hachiri
 */
public interface IAccountService {

    /**
     * Obtiene todas las cuentas asociadas a un cliente específico.
     *
     * @param customerId El ID del cliente del cual se desean obtener las cuentas.
     * @return Una lista de objetos AccountDto que representan las cuentas asociadas al cliente.
     */
    List<AccountDto> getAllAccountsByCustomerId(Long customerId);

    /**
     * Crea una nueva cuenta.
     *
     * @param accountDto El objeto AccountDto que contiene la información de la nueva cuenta.
     * @return El objeto AccountDto que representa la cuenta creada.
     */
    AccountDto createAccount(AccountDto accountDto);

    /**
     * Obtiene una cuenta por su ID.
     *
     * @param accountId El ID de la cuenta que se desea obtener.
     * @return El objeto AccountDto que representa la cuenta encontrada.
     */
    AccountDto getAccountById(Long accountId);

    AccountDto updateAccountById(Long accountId, AccountDto accountDto);

    AccountDto depositAccountById(Long accountId, Double depositAmount);

    AccountDto withdrawalAccountById(Long accountId, Double withdrawalAmount);

    /**
     * Elimina una cuenta por su ID.
     *
     * @param accountId El ID de la cuenta que se desea eliminar.
     */
    void deleteAccountById(Long accountId);
}