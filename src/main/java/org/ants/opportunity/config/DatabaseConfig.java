package org.ants.opportunity.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Profile("production")
@Configuration
public class DatabaseConfig extends AbstractMongoConfiguration {

    @Value("${MONGO_URI}")
    private String mongoUri;
    @Value("${MONGO_DATABASE}")
    private String database;

    @Bean(name = "mongo")
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}