package org.nttdata.credits_service.service;

import org.nttdata.credits_service.domain.dto.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    /**
     * Obtiene todos los créditos.
     *
     * @return Un Flux que emite todos los DTO de créditos.
     */
    Flux<CreditDto> listCreditsByOwnerId(String ownerId);

    /**
     * Crea un nuevo crédito.
     *
     * @param creditDto La información del crédito a crear.
     * @return Un Mono que emite el DTO del crédito creado.
     */
    Mono<CreditDto> createCredit(CreditDto creditDto);

    /**
     * Obtiene un crédito por su ID.
     *
     * @param creditId El ID del crédito a obtener.
     * @return Un Mono que emite el DTO del crédito encontrado.
     */
    Mono<CreditDto> getCreditById(String creditId);

    /**
     * Actualiza un crédito por su ID.
     *
     * @param creditId  El ID del crédito a actualizar.
     * @param creditDto La información actualizada del crédito.
     * @return Un Mono que emite el DTO del crédito actualizado.
     */
    Mono<CreditDto> updateCreditById(String creditId, CreditDto creditDto);

    /**
     * Elimina un crédito por su ID.
     *
     * @param creditId El ID del crédito a eliminar.
     * @return Un Mono que indica la finalización de la operación de eliminación.
     */
    Mono<Void> deleteCreditById(String creditId);
}
