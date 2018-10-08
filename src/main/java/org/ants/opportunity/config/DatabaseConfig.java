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
import org.ants.opportunity.model.Opportunity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;

import java.io.IOException;

@Configuration
public class DatabaseConfig extends AbstractMongoConfiguration {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 27017;
    private static MongoClient mongoClient;
    private static MongodExecutable mongodExecutable;
    private static MongoTemplate mongoTemplate;

    @Value("${MONGO_URI}")
    private String mongoUri;
    @Value("${MONGO_DATABASE}")
    private String database;
    @Value("${spring_profiles_active:local}")
    private String profile;

    @Bean("mongo")
    @Profile("production")
    public Mongo mongo() {
        return new MongoClient(new MongoClientURI(mongoUri));
    }

    @Bean("mongo")
    @Profile({"!production","!test"})
    public Mongo getLocalMongoSetup(){
        startLocalDatabase();
        connectToLocalDatabase();
        ensureIndexCreation();
        return mongoClient;
    }

    private void startLocalDatabase(){
        IMongodConfig mongodConfig = null;
        try {
            mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                    .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);

        try {
            mongodExecutable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ensureIndexCreation(){
        mongoTemplate = new MongoTemplate(mongoClient, "ants");
        //Ensure Opportunity Location Geospatial Index
        mongoTemplate.indexOps(Opportunity.class).ensureIndex( new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE) );
    }

    private void connectToLocalDatabase(){
        mongoClient = new MongoClient(IP, PORT);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

}
