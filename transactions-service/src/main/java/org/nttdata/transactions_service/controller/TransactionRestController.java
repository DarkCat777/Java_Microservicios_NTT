package org.nttdata.transactions_service.controller;

import lombok.RequiredArgsConstructor;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.service.ITransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionRestController {
    private final ITransactionService transactionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto createTransaction(@Validated TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionDto getTransactionById(@Positive @PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @GetMapping("/by-bank-product/{bankProductType}/{bankProductId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> listTransactionByBankProductId(
            @NotBlank @PathVariable String bankProductType,
            @Positive @PathVariable Long bankProductId) {
        return transactionService.listTransactionsByBankProductId(bankProductType.toUpperCase(), bankProductId);
    }

    @GetMapping("/count-transactions-in-current-month/{bankProductType}/{bankProductId}")
    @ResponseStatus(HttpStatus.OK)
    public Long countTransactionInCurrentMonth(
            @NotBlank @PathVariable String bankProductType,
            @Positive @PathVariable Long bankProductId) {
        return transactionService.countTransactionByBankProductIdAndCurrentMonth(bankProductType.toUpperCase(), bankProductId);
    }

    @DeleteMapping("/delete/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransactionById(@Positive @PathVariable Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
    }
}
