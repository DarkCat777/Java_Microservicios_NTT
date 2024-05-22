package org.nttdata.accounts_service.repository;

import org.nttdata.accounts_service.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Encuentra todas las cuentas asociadas a un cliente por su ID de cliente.
     *
     * @param ownerId El ID del cliente del cual se desean obtener las cuentas.
     * @return Un Flux que emite todas las cuentas asociadas al cliente especificado.
     */
    List<Account> findAllByOwnerId(Long ownerId);

    /**
     * Checks if an account exists for the given owner ID.
     *
     * @param ownerId the ID of the owner whose account is to be checked
     * @return {@code true} if an account exists for the given owner ID, {@code false} otherwise
     */
    Boolean existsAccountByOwnerId(Long ownerId);
}

