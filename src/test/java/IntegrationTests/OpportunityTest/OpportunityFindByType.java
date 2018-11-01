package IntegrationTests.OpportunityTest;

import Connection.DatabaseConnection;
import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import org.ants.opportunity.Application;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = {"classpath:test.properties"})
public class OpportunityFindByType {
    private static final String API_OPPORTUNITY_BASE = "/api/opportunity/";
    private static final String FIND_BY_TYPE_ID = "search/findByTypeId";
    private static final String FIND_BY_TYPE = "search/findByType";
    private static final Logger logger = LoggerFactory.getLogger(OpportunityFindByType.class);

    @BeforeClass
    public static void addFakeTypesAndOpportunities(){
        logger.info("Adding 10 random types");
        TypeLoader.addFakeElement(10);
        logger.info("Adding 10 random opportunities");
        OpportunityLoader.addFakeElement(10);

        logger.info("Adding type which will be looked for: Religious");
        Type type = new Type("Religious");
        TypeLoader.addElement(type);

        logger.info("Adding 2 opportunities associated with Religious type");

        Opportunity opportunity = new Opportunity();
        opportunity.setName("Instituto Federal de São Paulo - Quermece");
        opportunity.setType(type);
        opportunity.setLocation(new GeoJsonPoint(-47.2332798,-22.8511083));

        logger.info("Adding opportunity: "+opportunity.toString());
        OpportunityLoader.addElement(opportunity);

        opportunity = new Opportunity();
        opportunity.setName("City central - Quermece");
        opportunity.setType(type);
        opportunity.setLocation(new GeoJsonPoint(-47.2332798,-22.8511083));
        logger.info("Adding opportunity: "+opportunity.toString());
        OpportunityLoader.addElement(opportunity);
    }

    @Test
    public void
    findOpportunityByTypeUsingId() {
        logger.info("Testing finding opportunity by type ID - should return 2");

        MongoTemplate database = DatabaseConnection.getIntance();

        Type type = database.findOne(new BasicQuery("{ name : 'Religious' }"), Type.class);

        given().
                param("typeId",type.getId().toString()).
                when().
                get(API_OPPORTUNITY_BASE + FIND_BY_TYPE_ID).
                then().
                statusCode(200).
                body("content.size()", is(2));
    }

    @Test
    public void
    findOpportunityByTypeUsingName() {
        logger.info("Testing finding opportunity by type name should return 2");

        given().
                param("type","Religious").
                when().
                get(API_OPPORTUNITY_BASE + FIND_BY_TYPE).
                then().
                statusCode(200).
                body("content.size()", is(2));
    }
}
