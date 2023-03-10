package org.pjp.museum.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Profile("cosmos")
@EnableMongoRepositories(basePackages = "org.pjp.museum.repository")
@Configuration
public class CosmosConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "museumapp";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://rafmanston-mongodb:WoyfLPjgezQHtGDh7kijX6VtqYhdQ8LnHLRkqxEaPcwTyxwtq0QGL2lBgknSMw9Vcgl87cvn8Y3nACDb9z5F6A==@rafmanston-mongodb.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@rafmanston-mongodb@");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("org.pjp.museum.model");
    }
}