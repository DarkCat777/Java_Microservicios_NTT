package org.nttdata.accounts_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    private final IAccountService accountService;

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountDto> getAccountById(
            @PathVariable @NotBlank String accountId
    ) {
      log.info(String.format("GET - ACCOUNT BY ID: %s", accountId));
      return accountService.getAccountById(accountId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountDto> createAccount(
            @RequestBody @Validated AccountDto accountDto
    ) {
        log.info(String.format("CREATE - ACCOUNT WITH: %s", accountDto.toString()));
        return accountService.createAccount(accountDto);
    }

    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountDto> updateAccountById(
            @PathVariable @NotBlank String accountId,
            @RequestBody @Validated AccountDto accountDto
    ) {
        log.info(String.format("PUT - ACCOUNT BY ID: %s", accountId));
        return accountService.updateAccount(accountId, accountDto);
    }


    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccountById(@PathVariable @NotBlank String accountId) {
        log.info(String.format("DELETE - ACCOUNT BY ID: %s", accountId));
        return accountService.deleteAccountById(accountId);
    }
}
