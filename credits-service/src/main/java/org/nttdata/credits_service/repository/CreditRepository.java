package org.nttdata.credits_service.repository;

import org.nttdata.credits_service.domain.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Credit.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas en la base de datos.
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    /**
     * Encuentra todos los créditos asociados con el ID del propietario especificado.
     *
     * @param ownerId el ID del propietario cuyos créditos se desean encontrar
     * @return una lista de objetos Credit que representan los créditos del propietario
     */
    List<Credit> findAllByOwnerId(Long ownerId);

    /**
     * Verifica si existen créditos para un propietario específico y un tipo de crédito dado.
     *
     * @param ownerId el ID del propietario
     * @return {@code true} si existen créditos para el propietario y tipo de crédito especificados, {@code false} en caso contrario
     */
    Boolean existsCreditsByOwnerId(Long ownerId);
}