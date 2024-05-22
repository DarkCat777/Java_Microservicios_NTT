package org.nttdata.accounts_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

/**
 * Controlador REST para la gestión de productos de cuenta.
 * Proporciona endpoints para realizar operaciones CRUD en cuentas.
 *
 * @author Erick David Carpio Hachiri
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/account-products")
@RequiredArgsConstructor
public class AccountRestController {

    private final IAccountService accountService;

    /**
     * Obtiene una cuenta por su ID.
     *
     * @param accountId el ID de la cuenta a recuperar
     * @return la cuenta con el ID especificado
     */
    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto getAccountById(
            @PathVariable @Positive Long accountId
    ) {
        log.info(String.format("GET - ACCOUNT BY ID: %s", accountId));
        return accountService.getAccountById(accountId);
    }

    /**
     * Crea una nueva cuenta.
     *
     * @param accountDto los detalles de la cuenta a crear
     * @return la cuenta creada
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public AccountDto createAccount(
            @RequestBody @Validated AccountDto accountDto
    ) {
        log.info(String.format("CREATE - ACCOUNT WITH: %s", accountDto.toString()));
        return accountService.createAccount(accountDto);
    }

    /**
     * Actualiza una cuenta existente por su ID.
     *
     * @param accountId el ID de la cuenta a actualizar
     * @param accountDto los nuevos detalles de la cuenta
     * @return la cuenta actualizada
     */
    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccountById(
            @PathVariable @Positive Long accountId,
            @RequestBody @Validated AccountDto accountDto
    ) {
        log.info(String.format("PUT - ACCOUNT BALANCE BY ID: %s", accountId));
        return accountService.updateAccountById(accountId, accountDto);
    }

    /**
     * Realiza un depósito en una cuenta por su ID.
     *
     * @param accountId el ID de la cuenta
     * @param depositAmount la cantidad a depositar
     * @return la cuenta actualizada con el depósito
     */
    @PutMapping("/{accountId}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto depositAccountById(
            @PathVariable @Positive Long accountId,
            @RequestBody @Positive Double depositAmount
    ) {
        log.info(String.format("PUT - ACCOUNT DEPOSIT BY ID: %s", accountId));
        return accountService.depositAccountById(accountId, depositAmount);
    }

    /**
     * Realiza un retiro en una cuenta por su ID.
     *
     * @param accountId el ID de la cuenta
     * @param withdrawalAmount la cantidad a retirar
     * @return la cuenta actualizada con el retiro
     */
    @PutMapping("/{accountId}/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto withdrawalAccountById(
            @PathVariable @Positive Long accountId,
            @RequestBody @Positive Double withdrawalAmount
    ) {
        log.info(String.format("PUT - ACCOUNT WITHDRAWAL BY ID: %s", accountId));
        return accountService.withdrawalAccountById(accountId, withdrawalAmount);
    }

    /**
     * Elimina una cuenta por su ID.
     *
     * @param accountId el ID de la cuenta a eliminar
     */
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountById(@PathVariable @Positive Long accountId) {
        log.info(String.format("DELETE - ACCOUNT BY ID: %s", accountId));
        accountService.deleteAccountById(accountId);
    }
}
