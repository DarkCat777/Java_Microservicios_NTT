package org.nttdata.transactions_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.nttdata.transactions_service.domain.exception.NotFoundException;
import org.nttdata.transactions_service.domain.exception.BankProductServiceDownException;
import org.nttdata.transactions_service.domain.exception.InvalidTransactionException;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.domain.type.TransactionType;
import org.nttdata.transactions_service.mapper.ITransactionMapper;
import org.nttdata.transactions_service.repository.TransactionRepository;
import org.nttdata.transactions_service.service.IAccountRetrofitService;
import org.nttdata.transactions_service.service.ITransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.nttdata.transactions_service.domain.exception.NotFoundException.CUSTOMER_NOT_FOUND_TEMPLATE;
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

    /**
     * Referencia a {@link IAccountRetrofitService}
     */
    private final IAccountRetrofitService bankProductService;

    /**
     * Crea una transacción
     *
     * @param transactionDto es la información que se envía al endpoint para crear la transacción
     * @return retorna una instancia reactiva con el DTO transacción creado.
     */
    @Override
    public Mono<TransactionDto> createTransaction(TransactionDto transactionDto) {
        return bankProductService.getBankProductById(transactionDto.getProductId())
                .onErrorResume(originalError -> Mono.error(new BankProductServiceDownException("El microservicio de productos no esta disponible o presenta errores")))
                .publishOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(CUSTOMER_NOT_FOUND_TEMPLATE, transactionDto.getProductId()))
                ))
                .flatMap(bankProductDto -> {
                    if (transactionDto.getTransactionType().equals(TransactionType.DEPOSIT.toString())) {
                        bankProductDto.setBalance(bankProductDto.getBalance() + transactionDto.getAmount());
                    } else if (transactionDto.getTransactionType().equals(TransactionType.WITHDRAWAL.toString())) {
                        if (bankProductDto.getBalance() >= transactionDto.getAmount()) {
                            bankProductDto.setBalance(bankProductDto.getBalance() - transactionDto.getAmount());
                        } else {
                            return Mono.error(new InvalidTransactionException("Transacción invalida su balance es menor al retiro."));
                        }
                    } else {
                        return Mono.error(new InvalidTransactionException("El tipo de transacción es invalida."));
                    }
                    return bankProductService.updateBankProduct(bankProductDto)
                            .onErrorResume(originalError -> Mono.error(new BankProductServiceDownException("El microservicio de productos no esta disponible o presenta errores")))
                            .flatMap(bankProductUpdatedDto -> transactionRepository.save(transactionMapper.toEntity(transactionDto))
                                    .map(transactionMapper::toDto));
                });
    }

    /**
     * Actualiza una transacción
     *
     * @param transactionDto es la información que se envía al endpoint para actualizar la transacción
     * @return retorna una instancia reactiva con el DTO transacción actualizado.
     */
    @Override
    public Mono<TransactionDto> updateTransaction(TransactionDto transactionDto) {
        throw new RuntimeException("Method not implemented exception.");
    }

    /**
     * Obtiene una transacción
     *
     * @param transactionId es el id que se envía en el path del endpoint para obtener la transacción.
     * @return retorna una instancia reactiva con el DTO de la transacción.
     */
    @Override
    public Mono<TransactionDto> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(TRANSACTION_NOT_FOUND_TEMPLATE, transactionId))
                ))
                .publishOn(Schedulers.boundedElastic())
                .map(transactionMapper::toDto);
    }

    /**
     * Obtiene la lista de transacciones por el id del producto bancario.
     *
     * @param bankProductId es el id que se envía en el path del endpoint para obtener las transacciones.
     * @return retorna una instancia reactiva con los DTO's producto bancario creado.
     */
    @Override
    public Flux<TransactionDto> listTransactionsByBankProductId(String bankProductId) {
        return transactionRepository.findByBankProductId(bankProductId)
                .publishOn(Schedulers.boundedElastic())
                .map(transactionMapper::toDto);
    }

    /**
     * Elimina una transacción por el id de la transacción.
     *
     * @param transactionId es el id que se envía en el path del endpoint para eliminar la transacción.
     * @return retorna una instancia reactiva vacía.
     */
    @Override
    public Mono<Void> deleteTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(TRANSACTION_NOT_FOUND_TEMPLATE, transactionId))
                ))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(transaction -> transactionRepository.deleteById(transactionId));
    }
}
