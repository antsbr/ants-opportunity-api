package IntegrationTests.ExceptionHandlerTest;

import DataLoader.OpportunityLoader;
import IntegrationTests.OpportunityTest.OpportunityFindByType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ants.opportunity.Application;
import org.ants.opportunity.model.Opportunity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = {"classpath:test.properties"})
public class OpportunityCreationExceptions {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final String API_OPPORTUNITY_BASE = "/api/opportunity";
    private static final Logger logger = LoggerFactory.getLogger(OpportunityFindByType.class);

    @Test
    public void opportunityDoesNotHaveTypeAttributeException() throws JsonProcessingException {
        logger.debug("Test for when the opportunity does not have the type attribute");

        Opportunity opportunity = new Opportunity();

        String opportunityJson = jsonMapper.writeValueAsString(opportunity);

        given().
            body(opportunityJson).
            contentType("application/JSON").
            when().
            post(API_OPPORTUNITY_BASE).
            then().
            statusCode(400).
            body("debugMessage", is("Please find a valid type in /api/type")).
            body("message", is("The type attribute has not been found."));
    }

    @Test
    public void opportunityDoesNotHaveAValidTypeException() throws JsonProcessingException {
        logger.debug("Test for when the opportunity type does not exist in the database");

        Opportunity opportunity = OpportunityLoader.getFakeElement();
        opportunity.getType().setId(null);
        opportunity.getType().setName("Type not saved");

        String opportunityJson = jsonMapper.writeValueAsString(opportunity);

        given().
                body(opportunityJson).
                contentType("application/JSON").
                when().
                post(API_OPPORTUNITY_BASE).
                then().
                statusCode(400).
                body("debugMessage", is("Please find a valid type in /api/type")).
                body("message", is("The type with name '"+ opportunity.getType().getName() +"' was not found."));
    }
}
