package org.nttdata.transactions_service.service;

import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import reactor.core.publisher.Mono;
import retrofit2.http.*;

/**
 * Stub del controlador del microservicio de productos bancarios de tipo crédito
 */
@RetrofitClient("accounts-service")
public interface IAccountRetrofitService {
    /**
     * Endpoint que retorna todos los productos bancarios por el id del propietario de la cuenta.
     *
     * @param ownerId es el id del propietario de la cuenta.
     * @return retorna una instancia de reactiva de multiples elementos(productos bancarios)
     */
    @GET("/by-owner-id/{ownerId}")
    Flux<AccountDto> listAllAccountsByOwnerId(@Path("ownerId") String ownerId);

    /**
     * Endpoint que retorna el producto bancario por su id.
     *
     * @param accountId es el id del producto bancario de tipo crédito.
     * @return retorna una instancia de reactiva de multiples elementos(productos bancarios)
     */
    @GET("/{accountId}")
    Mono<AccountDto> getAccountById(@Path("accountId") String accountId);

    /**
     * Endpoint que crea y retorna el producto bancario por su id.
     *
     * @param accountDto es la información con la que se creara un producto bancario.
     * @return retorna una instancia de reactiva un producto bancario
     */
    @POST("/create")
    Mono<AccountDto> createAccount(@Body AccountDto accountDto);

    /**
     * Endpoint que actualiza y retorna el producto bancario por su id.
     *
     * @param accountDto es la información con la que se creara un producto bancario.
     * @return retorna una instancia de reactiva un producto bancario.
     */
    @PUT("/update")
    Mono<AccountDto> updateAccount(@Body AccountDto accountDto);

    /**
     * Endpoint que elimina el producto bancario por su id.
     *
     * @param accountId es el id del producto bancario.
     * @return retorna una instancia de reactiva vacía.
     */
    @DELETE("/{accountId}")
    Mono<Void> deleteAccountById(@Path("accountId") String accountId);
}
