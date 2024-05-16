package org.nttdata.credits_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.nttdata.credits_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.credits_service.domain.exception.NotFoundException;
import org.nttdata.credits_service.domain.type.CreditType;
import org.nttdata.credits_service.domain.type.CustomerType;
import org.nttdata.credits_service.mapper.ICreditMapper;
import org.nttdata.credits_service.repository.CreditRepository;
import org.nttdata.credits_service.service.ICreditService;
import org.nttdata.credits_service.service.ICustomerRetrofitService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

import static org.nttdata.credits_service.domain.exception.NotFoundException.CREDIT_NOT_FOUND_TEMPLATE;
import static org.nttdata.credits_service.domain.exception.NotFoundException.CUSTOMER_NOT_FOUND_TEMPLATE;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements ICreditService {

    private final CreditRepository creditRepository;

    private final ICustomerRetrofitService customerRetrofitService;

    private final ICreditMapper creditMapper;

    @Override
    public Flux<CreditDto> listCreditsByOwnerId(String ownerId) {
        return creditRepository.findAllByOwnerId(ownerId)
                .publishOn(Schedulers.boundedElastic())
                .map(creditMapper::toDto);
    }

    @Override
    public Mono<CreditDto> createCredit(CreditDto creditDto) {
        return customerRetrofitService.getCustomerById(creditDto.getOwnerId())
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CUSTOMER_NOT_FOUND_TEMPLATE, creditDto.getOwnerId()))))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(customerDto -> {
                    Map<String, String> validationErrors = this.isValidCreateCredit(customerDto, creditDto);
                    if (validationErrors.isEmpty()) {
                        return creditRepository.save(creditMapper.toEntity(creditDto))
                                .publishOn(Schedulers.boundedElastic())
                                .map(creditMapper::toDto);
                    } else {
                        return Mono.error(new BusinessLogicValidationException("Business logic invalid", validationErrors));
                    }
                });
    }

    /**
     * Valida la creación de un crédito para un cliente dado.
     *
     * @param customerDto El DTO del cliente que está solicitando el crédito.
     * @param creditDto   El DTO del crédito que se desea crear.
     * @return Un mapa con el resultado de la validación. Si la validación es exitosa,
     * el mapa estará vacío. Si hay errores de validación, el mapa contendrá
     * las claves de error y los mensajes correspondientes.
     */
    private Map<String, String> isValidCreateCredit(CustomerDto customerDto, CreditDto creditDto) {
        switch (CustomerType.valueOf(customerDto.getCustomerType())) {
            case PERSONAL:
                Boolean existCredit = creditRepository.existsCreditsByOwnerIdAndCreditType(creditDto.getOwnerId(), CreditType.CREDIT).block();
                if (Boolean.TRUE.equals(existCredit)) {
                    return Map.of("Invalid customer", "As a personal customer you can only have one credit");
                } else {
                    return Map.of();
                }
            case BUSINESS:
                return Map.of();
            default:
                return Map.of("Invalid customer type", "You do not have a valid client type assignment.");
        }
    }

    @Override
    public Mono<CreditDto> updateCreditById(String creditId, CreditDto creditDto) {
        return creditRepository.findById(creditId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(existingCredit -> creditRepository.save(creditMapper.partialUpdate(creditDto, existingCredit)).map(creditMapper::toDto));
    }

    @Override
    public Mono<CreditDto> getCreditById(String creditId) {
        return creditRepository.findById(creditId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))))
                .publishOn(Schedulers.boundedElastic())
                .map(creditMapper::toDto);
    }

    @Override
    public Mono<Void> deleteCreditById(String creditId) {
        return creditRepository.findById(creditId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))))
                .flatMap(creditRepository::delete);
    }
}
