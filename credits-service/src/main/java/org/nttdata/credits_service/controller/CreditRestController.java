package org.nttdata.credits_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.service.ICreditService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit-products/credits")
public class CreditRestController {

    private final ICreditService creditService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditDto createCredit(@Validated @RequestBody CreditDto creditDto) {
        log.info("Create credit {}", creditDto);
        return creditService.createCredit(creditDto);
    }

    @GetMapping("/{creditId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditDto getCreditById(@Positive @PathVariable Long creditId) {
        log.info("Get credit {}", creditId);
        return creditService.getCreditById(creditId);
    }

    @PutMapping("/{creditId}/payment")
    @ResponseStatus(HttpStatus.OK)
    public CreditDto paymentCredit(
            @Positive @PathVariable Long creditId,
            @Validated @RequestBody Double amount
    ) {
        log.info("Payment credit {}", amount);
        return creditService.paymentCredit(creditId, amount);
    }

    @PutMapping("/{creditId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditDto updateCredit(
            @Positive @PathVariable Long creditId,
            @Validated @RequestBody CreditDto creditDto
    ) {
        log.info("Update credit {}", creditDto);
        return creditService.updateCreditById(creditId, creditDto);
    }

    @DeleteMapping("/{creditId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCredit(@Positive @PathVariable Long creditId) {
        log.info("Delete credit {}", creditId);
        creditService.deleteCreditById(creditId);
    }
}