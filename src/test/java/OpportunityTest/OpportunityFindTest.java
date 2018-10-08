package OpportunityTest;

import Connection.DatabaseConnection;
import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import com.github.javafaker.Faker;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

public class OpportunityFindTest {
    private static final String API_SEARCH_OPPORTUNITY_BASE = "/api/opportunity/search/";
    private static final String FIND_BY_LOCATION_NEAR = "findByLocationNear";

    @BeforeAll
    static void loadData(){
        TypeLoader.addFakeType(10);
        OpportunityLoader.addFakeOpportunity(100);
    }

/*    @AfterAll
    static void clean(){
        mongoTemplate.dropCollection("opportunity");
        mongoTemplate.dropCollection("type");
    }

 /*   @AfterAll
    static void clean() {
        mongodExecutable.stop();
    }

 /*   @DisplayName("given object to save"
            + " when save object using MongoDB template"
            + " then object is saved")
    @Test
    void test() throws Exception {
        /* given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        //assert(1==1);
        //assert(mongoTemplate.findAll(DBObject.class, "collection")).get(0).equals("value");
    }*/

    @Test public void
    findOpportunityByLocationNearRadius() {
        given().
                param("latitude-longitude","2.435048,48.886112").
                param("maxdistance","20km").
                when().
                get(API_SEARCH_OPPORTUNITY_BASE+FIND_BY_LOCATION_NEAR).
                then().
                statusCode(404);/*.
        body("name", equalTo("Teste2"));*/

    }

}