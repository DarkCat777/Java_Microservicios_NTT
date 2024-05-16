package org.nttdata.transactions_service.service;

import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import reactor.core.publisher.Mono;
import retrofit2.http.*;

/**
 * Stub del controlador del microservicio de productos bancarios de tipo crédito
 */
@RetrofitClient("credits-service")
public interface ICreditRetrofitService {
    /**
     * Endpoint que retorna todos los productos bancarios por el id del propietario de la cuenta.
     *
     * @param ownerId es el id del propietario de la cuenta.
     * @return retorna una instancia de reactiva de multiples elementos(productos bancarios)
     */
    @GET("/by-owner-id/{ownerId}")
    Flux<CreditDto> listAllCreditsByOwnerId(@Path("ownerId") String ownerId);

    /**
     * Endpoint que retorna el producto bancario por su id.
     *
     * @param creditId es el id del producto bancario de tipo crédito.
     * @return retorna una instancia de reactiva de multiples elementos(productos bancarios)
     */
    @GET("/{creditId}")
    Mono<CreditDto> getCreditById(@Path("creditId") String creditId);

    /**
     * Endpoint que crea y retorna el producto bancario por su id.
     *
     * @param creditDto es la información con la que se creara un producto bancario.
     * @return retorna una instancia de reactiva un producto bancario
     */
    @POST("/create")
    Mono<CreditDto> createCredit(@Body CreditDto creditDto);

    /**
     * Endpoint que actualiza y retorna el producto bancario por su id.
     *
     * @param creditDto es la información con la que se creara un producto bancario.
     * @return retorna una instancia de reactiva un producto bancario.
     */
    @PUT("/update")
    Mono<CreditDto> updateCredit(@Body CreditDto creditDto);

    /**
     * Endpoint que elimina el producto bancario por su id.
     *
     * @param creditId es el id del producto bancario.
     * @return retorna una instancia de reactiva vacía.
     */
    @DELETE("/{creditId}")
    Mono<Void> deleteCreditById(@Path("creditId") String creditId);
}
