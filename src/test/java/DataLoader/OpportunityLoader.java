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

    public static void addFakeElement(int numberOfOpportunities){
        int numberOfTypesAvailable = (int)DATABASE.count(new Query(), Type.class);
        for(int i = 0;i<numberOfOpportunities; i++) {
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
            DATABASE.save(opportunity, "opportunity");
        }
    }

    public static void addElement(Opportunity opportunity){
        DATABASE.save(opportunity, "opportunity");
    }
}
