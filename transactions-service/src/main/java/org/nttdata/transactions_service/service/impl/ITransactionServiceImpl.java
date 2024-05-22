package org.nttdata.transactions_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.exception.NotFoundException;
import org.nttdata.transactions_service.domain.exception.InvalidTransactionException;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.nttdata.transactions_service.mapper.ITransactionMapper;
import org.nttdata.transactions_service.repository.TransactionRepository;
import org.nttdata.transactions_service.service.ITransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.nttdata.transactions_service.domain.exception.NotFoundException.TRANSACTION_NOT_FOUND_TEMPLATE;


@Service
@RequiredArgsConstructor
public class ITransactionServiceImpl implements ITransactionService {

    /**
     * Referencia a {@link ITransactionMapper}
     */
    private final ITransactionMapper transactionMapper;

    /**
     * Referencia a {@link ITransactionService}
     */
    private final TransactionRepository transactionRepository;

    private BankProductType getBankProductTypeFromString(String bankProductType) {
        switch (BankProductType.valueOf(bankProductType)) {
            case CREDIT_CARD:
                return BankProductType.CREDIT_CARD;
            case ACCOUNT:
                return BankProductType.ACCOUNT;
            case CREDIT:
                return BankProductType.CREDIT;
            default:
                throw new InvalidTransactionException("Invalid bank product type");
        }
    }


    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toEntity(transactionDto)));
    }

    @Override
    public TransactionDto getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TRANSACTION_NOT_FOUND_TEMPLATE, transactionId))
                );
    }

    @Override
    public List<TransactionDto> listTransactionsByBankProductId(String bankProductType, Long bankProductId) {
        return transactionRepository.findByBankProductTypeAndFromBankProductId(this.getBankProductTypeFromString(bankProductType), bankProductId)
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long countTransactionByBankProductIdAndCurrentMonth(String bankProductType, Long bankProductId) {
        LocalDate localDate = LocalDate.now();
        return transactionRepository.findByMonthAndBankProductIdAndBankProductType(localDate.getMonthValue(), localDate.getYear(), bankProductId , this.getBankProductTypeFromString(bankProductType));
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(TRANSACTION_NOT_FOUND_TEMPLATE, transactionId))
                );
        transactionRepository.delete(transaction);
    }
}
