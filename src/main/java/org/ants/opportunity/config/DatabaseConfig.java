package org.ants.opportunity.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import java.io.IOException;

@Configuration
public class DatabaseConfig extends AbstractMongoConfiguration {

    @Value("${MONGO_URI}")
    private String mongoUri;
    @Value("${MONGO_DATABASE}")
    private String database;
    @Value("${spring_profiles_active}")
    private String profile;

    @Bean("mongo")
    @Profile("production")
    public Mongo getMongoConnection() {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Bean("mongo")
    @Profile({"!production","!test"})
    public Mongo mongo(){
        String ip = "localhost";
        int port = 27017;

        IMongodConfig mongodConfig = null;
        try {
            mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                    .net(new Net(ip, port, Network.localhostIsIPv6()))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);

        try {
            mongodExecutable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MongoClient(ip, port);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
