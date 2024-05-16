package org.nttdata.transactions_service.service;

import org.nttdata.transactions_service.domain.dto.TransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface de operaciones del para el servicio de transacciones (CRUD y lectura de todos los productos bancarios)
 * @see TransactionDto
 * @see Mono
 * @see Flux
 * @author Erick David Carpio Hachiri
 */
public interface ITransactionService {

    /**
     * Crea una transacción
     *
     * @param transactionDto es la información que se envía al endpoint para crear la transacción
     * @return retorna una instancia reactiva con el DTO transacción creado.
     */
    Mono<TransactionDto> createTransaction(TransactionDto transactionDto);

    /**
     * Actualiza una transacción
     *
     * @param transactionDto es la información que se envía al endpoint para actualizar la transacción
     * @return retorna una instancia reactiva con el DTO transacción actualizado.
     */
    Mono<TransactionDto> updateTransaction(TransactionDto transactionDto);

    /**
     * Obtiene una transacción
     *
     * @param transactionId es el id que se envía en el path del endpoint para obtener la transacción.
     * @return retorna una instancia reactiva con el DTO de la transacción.
     */
    Mono<TransactionDto> getTransactionById(String transactionId);

    /**
     * Obtiene la lista de transacciones por el id del producto bancario.
     *
     * @param bankProductId es el id que se envía en el path del endpoint para obtener las transacciones.
     * @return retorna una instancia reactiva con los DTO's producto bancario creado.
     */
    Flux<TransactionDto> listTransactionsByBankProductId(String bankProductId);

    /**
     * Elimina una transacción por el id de la transacción.
     *
     * @param transactionId es el id que se envía en el path del endpoint para eliminar la transacción.
     * @return retorna una instancia reactiva vacía.
     */
    Mono<Void> deleteTransactionById(String transactionId);
}
