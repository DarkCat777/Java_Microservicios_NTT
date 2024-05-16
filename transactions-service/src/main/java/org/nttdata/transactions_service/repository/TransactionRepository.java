package org.nttdata.transactions_service.repository;

import org.nttdata.transactions_service.domain.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio de la entidad Transacción
 */
@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    /**
     * Obtiene todas las transacciones del producto bancario
     *
     * @param bankProductId Es el id del producto bancario del cual se busca obtener sus transacciones.
     * @return una instancia reactiva de multiples elementos de Transacción
     */
    Flux<Transaction> findByBankProductId(String bankProductId);
}
