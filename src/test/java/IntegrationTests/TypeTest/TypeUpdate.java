package IntegrationTests.TypeTest;

import DataLoader.OpportunityLoader;
import DataLoader.TypeLoader;
import IntegrationTests.OpportunityTest.OpportunityFindByType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ants.opportunity.Application;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = {"classpath:test.properties"})
public class TypeUpdate {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final String API_TYPE_BASE = "/api/type/";
    private static final String API_OPPORTUNITY_BASE = "/api/opportunity/";
    private static final String FIND_BY_TYPE = "search/findByType";
    private static final Logger logger = LoggerFactory.getLogger(OpportunityFindByType.class);

    @Test
    public void typeUpdateReflectTheOpportunitiesWhichBelongs() throws JsonProcessingException {
        logger.debug("Test for when the type has been updated and need to reflect in the opportunities");
        TypeLoader.addFakeElement(3);
        OpportunityLoader.addFakeElement(10);

        Type type = new Type();
        type.setName("Update Test Type");
        type = TypeLoader.addElement(type);

        Opportunity opportunity1 = OpportunityLoader.getFakeElement();
        Opportunity opportunity2 = OpportunityLoader.getFakeElement();
        Opportunity opportunity3 = OpportunityLoader.getFakeElement();
        opportunity1.setType(type);
        opportunity2.setType(type);
        opportunity3.setType(type);
        OpportunityLoader.addElement(opportunity1);
        OpportunityLoader.addElement(opportunity2);
        OpportunityLoader.addElement(opportunity3);

        type.setName("Update Test Type - After change");
        String typeJson = jsonMapper.writeValueAsString(type);

        given().
                body(typeJson).
                contentType("application/JSON").
                when().
                put(API_TYPE_BASE + type.getId()).
                then().
                statusCode(200);

        given().
                param("type","Update Test Type - After change").
                when().
                get(API_OPPORTUNITY_BASE + FIND_BY_TYPE).
                then().
                statusCode(200).
                body("content.size()", is(3));


    }
}
