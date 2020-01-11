package org.ants.opportunity.bootstrap;

import com.mongodb.MongoClient;

import org.ants.opportunity.model.Opportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBootstrap implements CommandLineRunner {

    private MongoClient mongoClient;

    @Autowired
    public DatabaseBootstrap(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
	}

    @Override
	public void run(String... args) throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "ants");
        //Ensure Opportunity Location Geospatial Index
        mongoTemplate.indexOps(Opportunity.class).ensureIndex( new GeospatialIndex("location").typed(GeoSpatialIndexType.GEO_2DSPHERE) );
    }
}
