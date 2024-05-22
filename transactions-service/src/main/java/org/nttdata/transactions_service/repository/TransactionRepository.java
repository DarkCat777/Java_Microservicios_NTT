package org.nttdata.transactions_service.repository;

import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de la entidad Transacción
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBankProductTypeAndFromBankProductId(BankProductType bankProductType, Long fromBankProductId);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE " +
            "MONTH(t.transactionDate) = :month AND " +
            "YEAR(t.transactionDate) = :year AND " +
            "(t.fromBankProductId = :bankProductId OR t.toBankProductId = :bankProductId) AND " +
            "t.bankProductType = :bankProductType")
    Long findByMonthAndBankProductIdAndBankProductType(
            int month,
            int year,
            Long bankProductId,
            BankProductType bankProductType);
}
