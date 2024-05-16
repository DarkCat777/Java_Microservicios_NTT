package org.nttdata.accounts_service.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.lang.NonNull;

@EnableReactiveMongoRepositories
public class MongoReactiveApplication extends AbstractReactiveMongoConfiguration {

    @Value("${mongodb.database:accounts}")
    private String databaseName;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @NonNull
    @Override
    protected String getDatabaseName() {
        return this.databaseName;
    }

}