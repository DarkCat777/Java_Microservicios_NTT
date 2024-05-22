package org.nttdata.credits_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.credits_service.domain.dto.CreditCardDto;
import org.nttdata.credits_service.service.ICreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/credit-products/credit-cards")
@RequiredArgsConstructor
public class CreditCardRestController {

    private final ICreditCardService creditCardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardDto createCreditCard(@Validated @RequestBody CreditCardDto creditCardDto) {
        log.info("Create credit card {}", creditCardDto);
        return creditCardService.createCreditCard(creditCardDto);
    }

    @GetMapping("/{creditCardId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardDto getCreditCardById(@Positive @PathVariable Long creditCardId) {
        log.info("Get credit card {}", creditCardId);
        return creditCardService.getCreditCardById(creditCardId);
    }

    @PutMapping("/{creditCardId}/credit-card-payment")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardDto paymentCreditCard(
            @Positive @PathVariable Long creditCardId,
            @Positive @RequestBody Double amount
    ) {
        log.info("Payment credit card {}", amount);
        return creditCardService.paymentCreditCard(creditCardId, amount);
    }

    @PutMapping("/{creditCardId}/charge-to-credit-card")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardDto chargeToCreditCard(
            @Positive @PathVariable Long creditCardId,
            @Positive @RequestBody Double amount
    ) {
        log.info("Charge to credit card {}", amount);
        return creditCardService.chargeToCreditCard(creditCardId, amount);
    }

    @PutMapping("/{creditCardId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardDto updateCreditCard(
            @Positive @PathVariable Long creditCardId,
            @Validated @RequestBody CreditCardDto creditCardDto
    ) {
        log.info("Update credit card {}", creditCardDto);
        return creditCardService.updateCreditCardById(creditCardId, creditCardDto);
    }

    @DeleteMapping("/{creditCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCreditCard(@Positive @PathVariable Long creditCardId) {
        log.info("Delete credit card {}", creditCardId);
        creditCardService.deleteCreditCardById(creditCardId);
    }
}
