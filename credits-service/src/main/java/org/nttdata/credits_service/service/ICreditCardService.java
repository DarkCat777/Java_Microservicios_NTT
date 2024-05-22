package org.nttdata.credits_service.service;

import org.nttdata.credits_service.domain.dto.CreditCardDto;

/**
 * Interfaz para el servicio de tarjetas de crédito que define las operaciones relacionadas con tarjetas de crédito.
 *
 * @author Erick David Carpio Hachiri
 */
public interface ICreditCardService {
    /**
     * Crea una nueva tarjeta de crédito en el sistema utilizando la información proporcionada en el objeto CreditCardDto.
     *
     * @param creditCardDto El objeto CreditCardDto que contiene la información de la tarjeta de crédito que se creará.
     * @return El objeto CreditCardDto que representa la tarjeta de crédito recién creada.
     */
    CreditCardDto createCreditCard(CreditCardDto creditCardDto);

    /**
     * Obtiene la información de una tarjeta de crédito específica basada en su identificador único.
     *
     * @param creditCardId El identificador único de la tarjeta de crédito que se desea recuperar.
     * @return El objeto CreditCardDto que contiene la información de la tarjeta de crédito encontrada.
     */
    CreditCardDto getCreditCardById(Long creditCardId);

    CreditCardDto updateCreditCardById(Long creditCardId, CreditCardDto creditCardDto);

    CreditCardDto paymentCreditCard(Long creditCardId, Double amount);

    CreditCardDto chargeToCreditCard(Long creditCardId, Double amount);


    /**
     * Elimina una tarjeta de crédito específica del sistema basada en su identificador único.
     *
     * @param creditCardId El identificador único de la tarjeta de crédito que se desea eliminar.
     */
    void deleteCreditCardById(Long creditCardId);

}
