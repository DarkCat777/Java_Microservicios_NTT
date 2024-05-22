package org.nttdata.transactions_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@OpenAPIDefinition
@EnableEurekaClient
@SpringBootApplication
public class TransactionsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsServiceApplication.class, args);
    }

}
