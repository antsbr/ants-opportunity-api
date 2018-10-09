package OpportunityTest;

import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class OpportunityFindTest {
    private static final String API_OPPORTUNITY_BASE = "/api/opportunity/";
    private static final String FIND_BY_LOCATION_NEAR = "search/findByLocationNear";

    @AfterAll
    static void clean(){
        TypeLoader.removeAllElements();
        OpportunityLoader.removeAllElements();
    }

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