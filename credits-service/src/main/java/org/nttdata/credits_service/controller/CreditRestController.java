package org.nttdata.credits_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.service.ICreditService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/credits")
public class CreditRestController {

    private final ICreditService creditService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditDto> createCredit(@Validated @RequestBody CreditDto creditDto) {
        log.info("Create credit {}", creditDto);
        return creditService.createCredit(creditDto);
    }

    @GetMapping("/{creditId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CreditDto> getCreditById(@NotBlank @PathVariable String creditId) {
        log.info("Get credit {}", creditId);
        return creditService.getCreditById(creditId);
    }

    @PutMapping("/{creditId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CreditDto> updateCredit(
            @NotBlank @PathVariable String creditId,
            @Validated @RequestBody CreditDto creditDto
    ) {
        log.info("Update credit {}", creditDto);
        return creditService.updateCreditById(creditId, creditDto);
    }

    @DeleteMapping("/{creditId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCredit(@NotBlank @PathVariable String creditId) {
        log.info("Delete credit {}", creditId);
        return creditService.deleteCreditById(creditId);
    }
}