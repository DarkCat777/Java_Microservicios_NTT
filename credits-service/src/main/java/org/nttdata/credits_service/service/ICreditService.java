package org.nttdata.credits_service.service;

import org.nttdata.credits_service.domain.dto.CreditDto;

import java.util.List;

/**
 * Interfaz para el servicio de créditos que define las operaciones relacionadas con créditos.
 *
 * @author Erick David Carpio Hachiri
 */
public interface ICreditService {

    /**
     * Lista todos los créditos asociados con el ID del propietario especificado.
     *
     * @param ownerId el ID del propietario cuyos créditos se desean listar
     * @return una lista de objetos CreditDto que representan los créditos del propietario
     */
    List<CreditDto> listCreditsByOwnerId(Long ownerId);

    /**
     * Crea un nuevo crédito.
     *
     * @param creditDto el objeto CreditDto que contiene los detalles del crédito a crear
     * @return el objeto CreditDto que representa el crédito creado
     */
    CreditDto createCredit(CreditDto creditDto);

    /**
     * Obtiene los detalles de un crédito específico por su ID.
     *
     * @param creditId el ID del crédito que se desea obtener
     * @return el objeto CreditDto que representa el crédito solicitado
     */
    CreditDto getCreditById(Long creditId);

    /**
     * Actualiza los detalles de un crédito específico por su ID.
     *
     * @param creditId el ID del crédito que se desea actualizar
     * @param creditDto el objeto CreditDto que contiene los nuevos detalles del crédito
     * @return el objeto CreditDto que representa el crédito actualizado
     */
    CreditDto updateCreditById(Long creditId, CreditDto creditDto);

    /**
     * Elimina un crédito específico por su ID.
     *
     * @param creditId el ID del crédito que se desea eliminar
     */
    void deleteCreditById(Long creditId);

    CreditDto paymentCredit(Long creditId, Double creditDto);
}
