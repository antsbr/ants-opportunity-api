package DataLoader;

import Connection.DatabaseConnection;
import com.github.javafaker.Faker;
import org.ants.opportunity.model.Opportunity;
import org.ants.opportunity.model.Type;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Random;

public class OpportunityLoader {
    private static final MongoTemplate DATABASE = DatabaseConnection.getIntance();
    private static final Faker FAKER = new Faker();
    private static final String OPPORTUNITY_COLLECTION = "opportunity";

    public static void addFakeElement(int numberOfOpportunities){
        for(int i = 0;i<numberOfOpportunities; i++) {
            DATABASE.save(getFakeElement(), OPPORTUNITY_COLLECTION);
        }
    }

    public static void addElement(Opportunity opportunity){
        DATABASE.save(opportunity, OPPORTUNITY_COLLECTION);
    }

    public static Opportunity getFakeElement(){
        Opportunity opportunity = createFakeElement();
        DATABASE.insert(opportunity);
        return opportunity;
    }

    public static void removeElement(Opportunity opportunity){
        DATABASE.remove(opportunity);
    }

    public static void removeAllElements(){
        DATABASE.remove(new Query(),OPPORTUNITY_COLLECTION);
    }

    private static Opportunity createFakeElement(){
        int numberOfTypesAvailable = (int)DATABASE.count(new Query(), Type.class);
        // Select a random type for each opportunity
        Query query = new Query();
        query.limit(-1);
        query.skip(new Random().nextInt(numberOfTypesAvailable));
        Type type = DATABASE.find(query, Type.class).get(0);
        // Creates the opportunity
        Opportunity opportunity = new Opportunity();
        opportunity.setName(FAKER.beer().name());
        opportunity.setType(type);
        opportunity.setLocation(new GeoJsonPoint(Double.parseDouble(FAKER.address().longitude()),
                Double.parseDouble(FAKER.address().latitude())
        ));
        return opportunity;
    }
}
