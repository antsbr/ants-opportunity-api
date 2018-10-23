package OpportunityTest;

import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import org.ants.opportunity.Application;
import org.ants.opportunity.model.Opportunity;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = {"classpath:test.properties"})
public class OpportunityFindTest {
    private static final String API_OPPORTUNITY_BASE = "/api/opportunity/";
    private static final String FIND_BY_LOCATION_NEAR = "search/findByLocationNear";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void
    findOpportunityByLocationNearRadius() {
        logger.info("Testing finding opportunity 1km location radius");

        Opportunity opportunity = new Opportunity();
        opportunity.setName("Instituto Federal de São Paulo - Quermece");
        opportunity.setType(TypeLoader.getFakeElement());
        opportunity.setLocation(new GeoJsonPoint(-47.2332798,-22.8511083));
        logger.info("Adding opportunity: "+opportunity.toString());
        OpportunityLoader.addElement(opportunity);

        logger.info("Adding opportunities more than 1km away");
        opportunity = new Opportunity();
        opportunity.setName("City central - Quermece");
        opportunity.setType(TypeLoader.getFakeElement());
        opportunity.setLocation(new GeoJsonPoint(-47.212767116725445,-22.8608663540715));
        logger.info("Adding opportunity: "+opportunity.toString());
        OpportunityLoader.addElement(opportunity);

        given().
                param("latitude-longitude","-22.8503613,-47.2325119").
                param("maxdistance","1km").
        when().
                get(API_OPPORTUNITY_BASE+FIND_BY_LOCATION_NEAR).
        then().
                statusCode(200).
                body("content.name", hasItems("Instituto Federal de São Paulo - Quermece")).
                body("content.size()", is(1));
    }

    /*@Test public void
    findOpportunityByLocationNearRadius2() {
        logger.info("Oi");
        Opportunity opportunity = new Opportunity();
        opportunity.setName("Instituto Federal de São Paulo - Quermece");
        opportunity.setType(TypeLoader.getFakeElement());
        opportunity.setLocation(new GeoJsonPoint(-47.2332798,-22.8511083));
        OpportunityLoader.addElement(opportunity);

        given().
                param("latitude-longitude","-22.8503613,-47.2325119").
                param("maxdistance","1km").
                when().
                get(API_OPPORTUNITY_BASE+FIND_BY_LOCATION_NEAR).
                then().
                statusCode(200).
                body("content.name", hasItems("Instituto Federal de São Paulo - Quermece")).
                body("content.size()", is(1));
    }*/

}