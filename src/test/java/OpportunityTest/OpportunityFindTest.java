package OpportunityTest;

import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import org.ants.opportunity.Application;
import org.ants.opportunity.model.Opportunity;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test public void
    findOpportunityByLocationNearRadius() {
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
    }

}