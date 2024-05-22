package org.nttdata.credits_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.credits_service.domain.dto.CreditCardDto;
import org.nttdata.credits_service.domain.dto.TransactionDto;
import org.nttdata.credits_service.domain.entity.CreditCard;
import org.nttdata.credits_service.domain.exception.BusinessLogicValidationException;
import org.nttdata.credits_service.domain.exception.NotFoundException;
import org.nttdata.credits_service.domain.type.BankProductType;
import org.nttdata.credits_service.domain.type.TransactionType;
import org.nttdata.credits_service.mapper.ICreditCardMapper;
import org.nttdata.credits_service.repository.CreditCardRepository;
import org.nttdata.credits_service.service.ICreditCardService;
import org.nttdata.credits_service.service.feign.TransactionFeignClient;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.nttdata.credits_service.domain.exception.NotFoundException.CREDIT_CARD_NOT_FOUND_TEMPLATE;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements ICreditCardService {

    private final CreditCardRepository creditCardRepository;

    private final TransactionFeignClient transactionService;

    private final ICreditCardMapper creditCardMapper;

    @Override
    public CreditCardDto createCreditCard(CreditCardDto creditCardDto) {
        return creditCardMapper.toDto(creditCardRepository.save(creditCardMapper.toEntity(creditCardDto)));
    }

    @Override
    public CreditCardDto getCreditCardById(Long creditCardId) {
        return creditCardRepository.findById(creditCardId).map(creditCardMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format(CREDIT_CARD_NOT_FOUND_TEMPLATE, creditCardId)));
    }

    @Override
    public CreditCardDto updateCreditCardById(Long creditCardId, CreditCardDto creditCardDto) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new NotFoundException(String.format(CREDIT_CARD_NOT_FOUND_TEMPLATE, creditCardId)));
        return creditCardMapper.toDto(creditCardRepository.save(creditCardMapper.partialUpdate(creditCardDto, creditCard)));
    }

    @Override
    public CreditCardDto paymentCreditCard(Long creditCardId, Double paymentAmount) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new NotFoundException(String.format(CREDIT_CARD_NOT_FOUND_TEMPLATE, creditCardId)));
        if (creditCard.getOutstandingBalance() >= paymentAmount) {
            creditCard.setOutstandingBalance(creditCard.getOutstandingBalance() - paymentAmount);
            transactionService.createTransaction(new TransactionDto(null, paymentAmount, TransactionType.PAYMENT.name(), BankProductType.CREDIT_CARD.name(), creditCardId, null, null));
            return creditCardMapper.toDto(creditCardRepository.save(creditCard));
        } else {
            throw new BusinessLogicValidationException("Invalid payment amount", Map.of("Payment amount", "You cannot make a payment greater than the debt"));
        }
    }

    @Override
    public CreditCardDto chargeToCreditCard(Long creditCardId, Double chargeAmount) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new NotFoundException(String.format(CREDIT_CARD_NOT_FOUND_TEMPLATE, creditCardId)));
        if (creditCard.getOutstandingBalance() + chargeAmount <= creditCard.getBalance()) {
            creditCard.setOutstandingBalance(creditCard.getOutstandingBalance() + chargeAmount);
            transactionService.createTransaction(new TransactionDto(null, chargeAmount, TransactionType.CHARGE.name(), BankProductType.CREDIT_CARD.name(), creditCardId, null, null));
            return creditCardMapper.toDto(creditCardRepository.save(creditCard));
        } else {
            throw new BusinessLogicValidationException("Invalid charge amount", Map.of("Charge amount", "You cannot make a charge greater than your balance"));
        }
    }

    @Override
    public void deleteCreditCardById(Long creditCardId) {
        CreditCard creditCard = creditCardRepository.findById(creditCardId)
                .orElseThrow(() -> new NotFoundException(String.format(CREDIT_CARD_NOT_FOUND_TEMPLATE, creditCardId)));
        creditCardRepository.delete(creditCard);
    }
}
