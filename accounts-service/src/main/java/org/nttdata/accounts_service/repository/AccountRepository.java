package org.nttdata.accounts_service.repository;

import org.nttdata.accounts_service.domain.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    /**
     * Encuentra todas las cuentas asociadas a un cliente por su ID de cliente.
     *
     * @param customerId El ID del cliente del cual se desean obtener las cuentas.
     * @return Un Flux que emite todas las cuentas asociadas al cliente especificado.
     */
    Flux<Account> findAllByOwnerId(String customerId);

    Mono<Boolean> existsAccountByOwnerId(String customerId);
}

