package org.nttdata.credits_service.service;

import org.nttdata.credits_service.domain.dto.CustomerDto;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import retrofit2.http.*;

@RetrofitClient("customers-service")
public interface ICustomerRetrofitService {
    /**
     * Retorna todos los clientes.
     *
     * @return Una instancia reactiva de múltiples elementos (clientes).
     */
    @GET("/all")
    Flux<CustomerDto> getCustomers();

    /**
     * Retorna un cliente por su ID.
     *
     * @param customerId El ID del cliente a obtener.
     * @return Una instancia reactiva de un elemento (cliente).
     */
    @GET("/{customerId}")
    Mono<CustomerDto> getCustomerById(@Path("customerId") String customerId);

    /**
     * Crea un cliente.
     *
     * @param customerDto La información del cliente a crear.
     * @return Una instancia reactiva de un elemento (cliente) creado.
     */
    @POST
    Mono<CustomerDto> createCustomer(@Body CustomerDto customerDto);

    /**
     * Actualiza un cliente por su ID.
     *
     * @param customerId El ID del cliente a actualizar.
     * @param customerDto La información actualizada del cliente.
     * @return Una instancia reactiva de un elemento (cliente) actualizado.
     */
    @PUT("/{customerId}")
    Mono<CustomerDto> updateCustomer(@Path("customerId") String customerId, @Body CustomerDto customerDto);

    /**
     * Elimina un cliente por su ID.
     *
     * @param customerId El ID del cliente a eliminar.
     * @return Una instancia reactiva de un elemento (vacío).
     */
    @DELETE("/{customerId}")
    Mono<Void> deleteCustomer(@Path("customerId") String customerId);

}
