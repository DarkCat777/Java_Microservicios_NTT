package org.nttdata.accounts_service.service.feign;

import org.nttdata.accounts_service.domain.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "transactions-service", path = "/api/transactions")
public interface TransactionFeignClient {

    @PostMapping("/create")
    TransactionDto createTransaction(TransactionDto transactionDto);

    @GetMapping("/{transactionId}")
    TransactionDto getTransactionById(@PathVariable Long transactionId);

    @GetMapping("/by-bank-product/{bankProductType}/{bankProductId}")
    List<TransactionDto> listTransactionByBankProductId(@PathVariable String bankProductType, @PathVariable Long bankProductId);

    @GetMapping("/count-transactions-in-current-month/{bankProductType}/{bankProductId}")
    Long countTransactionInCurrentMonth(@PathVariable String bankProductType, @PathVariable Long bankProductId);

    @DeleteMapping("/delete/{transactionId}")
    void deleteTransactionById(@PathVariable Long transactionId);
}

