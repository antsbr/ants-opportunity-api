package DataLoader;

import Connection.DatabaseConnection;
import com.github.javafaker.Faker;
import org.ants.opportunity.model.Type;
import org.springframework.data.mongodb.core.MongoTemplate;

public class TypeLoader {
    private static final MongoTemplate DATABASE = DatabaseConnection.getIntance();
    private static final Faker FAKER = new Faker();

    public static void addFakeType(int numberOfTypes){
        for(int i = 0;i < numberOfTypes; i++) {
            Type type = new Type();
            type.setName(FAKER.funnyName().name());
            DATABASE.save(type, "type");
        }
    }

}
