package org.nttdata.accounts_service.service;

import org.nttdata.accounts_service.domain.dto.AccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface de operaciones del para el servicio de cuentas bancarias (CRUD y lectura de todos los clientes)
 * @see AccountDto
 * @see Mono
 * @see Flux
 * @author Erick David Carpio Hachiri
 */
public interface IAccountService {

    /**
     * Obtiene todas las cuentas asociadas a un cliente.
     *
     * @param customerId Identificador del cliente.
     * @return Un Flux que emite todas las cuentas asociadas al cliente.
     */
    Flux<AccountDto> getAllAccountsByCustomerId(String customerId);

    /**
     * Crea una nueva cuenta.
     *
     * @param accountDto Los datos de la cuenta a crear.
     * @return Un Mono que emite la cuenta creada.
     */
    Mono<AccountDto> createAccount(AccountDto accountDto);

    /**
     * Obtiene una cuenta por su identificador.
     *
     * @param accountId Identificador de la cuenta.
     * @return Un Mono que emite la cuenta correspondiente al identificador especificado.
     */
    Mono<AccountDto> getAccountById(String accountId);

    /**
     * Actualiza los datos de una cuenta existente.
     *
     * @param accountId Identificador de la cuenta a actualizar.
     * @param accountDto Los nuevos datos de la cuenta.
     * @return Un Mono que emite la cuenta actualizada.
     */
    Mono<AccountDto> updateAccount(String accountId, AccountDto accountDto);

    /**
     * Elimina una cuenta por su identificador.
     *
     * @param accountId Identificador de la cuenta a eliminar.
     * @return Un Mono que indica la finalización de la operación de eliminación.
     */
    Mono<Void> deleteAccountById(String accountId);
}