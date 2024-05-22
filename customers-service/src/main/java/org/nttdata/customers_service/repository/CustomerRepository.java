package org.nttdata.customers_service.repository;

import org.nttdata.customers_service.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repositorio de la entidad Cliente
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Verifica si todas las entidades con los identificadores especificados están presentes en la base de datos.
     *
     * @param ids Conjunto de identificadores de las entidades a verificar
     * @return true si todas las entidades con los identificadores especificados existen en la base de datos, false de lo contrario
     */
    Boolean existsAllByIdIn(Set<Long> ids);
}