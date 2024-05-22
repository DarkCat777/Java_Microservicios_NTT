package org.nttdata.credits_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.nttdata.credits_service.domain.dto.TransactionDto;
import org.nttdata.credits_service.domain.entity.Credit;
import org.nttdata.credits_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.credits_service.domain.exception.NotFoundException;
import org.nttdata.credits_service.domain.type.BankProductType;
import org.nttdata.credits_service.domain.type.CustomerType;
import org.nttdata.credits_service.domain.type.TransactionType;
import org.nttdata.credits_service.mapper.ICreditMapper;
import org.nttdata.credits_service.repository.CreditRepository;
import org.nttdata.credits_service.service.ICreditService;
import org.nttdata.credits_service.service.feign.CustomerFeignClient;
import org.nttdata.credits_service.service.feign.TransactionFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.nttdata.credits_service.domain.exception.NotFoundException.CREDIT_NOT_FOUND_TEMPLATE;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements ICreditService {

    private final CreditRepository creditRepository;

    private final CustomerFeignClient customerService;

    private final TransactionFeignClient transactionService;

    private final ICreditMapper creditMapper;

    @Override
    public List<CreditDto> listCreditsByOwnerId(Long ownerId) {
        return creditRepository.findAllByOwnerId(ownerId).stream().map(creditMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CreditDto createCredit(CreditDto creditDto) {
        CustomerDto customerDto = customerService.getCustomerById(creditDto.getOwnerId());
        Map<String, String> validationErrors = this.isValidCreateCredit(customerDto, creditDto);
        if (validationErrors.isEmpty()) {
            return creditMapper.toDto(creditRepository.save(creditMapper.toEntity(creditDto)));
        } else {
            throw new BusinessLogicValidationException("Business logic invalid", validationErrors);
        }
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
                Boolean existCredit = creditRepository.existsCreditsByOwnerId(creditDto.getOwnerId());
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
    @Transactional
    public CreditDto updateCreditById(Long creditId, CreditDto creditDto) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> (new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))));
        return creditMapper.toDto(creditRepository.save(creditMapper.partialUpdate(creditDto, credit)));
    }

    @Override
    public CreditDto getCreditById(Long creditId) {
        return creditRepository.findById(creditId)
                .map(creditMapper::toDto)
                .orElseThrow(() -> (new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))));
    }

    @Override
    @Transactional
    public void deleteCreditById(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> (new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))));
        creditRepository.delete(credit);
    }

    @Override
    @Transactional
    public CreditDto paymentCredit(Long creditId, Double paymentAmount) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> (new NotFoundException(String.format(CREDIT_NOT_FOUND_TEMPLATE, creditId))));
        if (paymentAmount <= credit.getOutstandingBalance()) {
            credit.setOutstandingBalance(credit.getOutstandingBalance() - paymentAmount);
            transactionService.createTransaction(new TransactionDto(null, paymentAmount, TransactionType.PAYMENT_IN_INSTALLMENTS.name(), BankProductType.CREDIT.name(), creditId, null, null));
            return creditMapper.toDto(creditRepository.save(credit));
        } else {
            throw new BusinessLogicValidationException("Payment Amount", Map.of("Invalid payment amount", "Payment amount exceeds payment limit"));
        }
    }
}
