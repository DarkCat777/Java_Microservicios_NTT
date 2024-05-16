package org.nttdata.customers_service.repository;

import org.nttdata.customers_service.domain.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Set;

/**
 * Repositorio de la entidad Cliente
 */
@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    /**
     * Obtiene todos los clientes que tengan los ids de la colección.
     *
     * @param ids Ids de los que se busca las entidades Cliente
     * @return instancia reactiva de multiples elementos Cliente
     */
    Flux<Customer> findAllByIdIn(Set<String> ids);
}