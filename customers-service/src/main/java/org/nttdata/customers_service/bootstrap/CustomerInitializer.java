package org.nttdata.customers_service.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nttdata.customers_service.domain.entity.Customer;
import org.nttdata.customers_service.domain.type.CustomerType;
import org.nttdata.customers_service.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Población de la base de datos con ejemplos de datos puntuales.
 */
@Component
public class CustomerInitializer implements CommandLineRunner {

    private final CustomerRepository clientRepository;

    public CustomerInitializer(final CustomerRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws RuntimeException on error
     */
    @Override
    public void run(String... args) throws RuntimeException {
        clientRepository.deleteAll();
        String json = "[{\"surnames\":\"Zachery\",\"lastnames\":\"Karina\"},{\"surnames\":\"Ina\",\"lastnames\":\"Medge\"},{\"surnames\":\"Kenyon\",\"lastnames\":\"Noah\"},{\"surnames\":\"Chaney\",\"lastnames\":\"Lars\"},{\"surnames\":\"Colton\",\"lastnames\":\"Preston\"}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Customer[] clientsPersonal;
        try {
            clientsPersonal = objectMapper.readValue(json, Customer[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (Customer customer : clientsPersonal) {
            customer.setCustomerType(CustomerType.PERSONAL);
            clientRepository.save(customer);
        }
        String newJson = "[{\"surnames\":\"Irene\",\"lastnames\":\"Quinlan\"},{\"surnames\":\"Charles\",\"lastnames\":\"Uta\"},{\"surnames\":\"Trevor\",\"lastnames\":\"Michelle\"},{\"surnames\":\"Hoyt\",\"lastnames\":\"Inga\"},{\"surnames\":\"Charissa\",\"lastnames\":\"Macon\"}]";
        Customer[] clientsBusiness;
        try {
            clientsBusiness = objectMapper.readValue(newJson, Customer[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (Customer customer : clientsBusiness) {
            customer.setCustomerType(CustomerType.BUSINESS);
            clientRepository.save(customer);
        }
    }
}
