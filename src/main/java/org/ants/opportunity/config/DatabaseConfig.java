package org.ants.opportunity.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class DatabaseConfig extends AbstractMongoConfiguration {

    @Value("${MONGO_URI}")
    private String mongoUri;
    @Value("${MONGO_DATABASE}")
    private String database;

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
