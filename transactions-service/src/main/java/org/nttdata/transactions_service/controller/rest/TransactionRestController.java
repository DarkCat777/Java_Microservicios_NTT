package org.nttdata.transactions_service.controller.rest;

import lombok.RequiredArgsConstructor;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.domain.dto.operation.Create;
import org.nttdata.transactions_service.service.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.groups.Default;

@Validated
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionRestController {
    private final ITransactionService transactionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionDto> createTransaction(@Validated({Create.class, Default.class}) TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionDto> getTransactionById(@PathVariable String transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @GetMapping("/by-bank-product/{bankProductId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionDto> listTransactionByBankProductId(@PathVariable String bankProductId) {
        return transactionService.listTransactionsByBankProductId(bankProductId);
    }

    @DeleteMapping("/delete/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTransactionById(@PathVariable String transactionId) {
        return transactionService.deleteTransactionById(transactionId);
    }
}
