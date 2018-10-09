package DataLoader;

import Connection.DatabaseConnection;
import com.github.javafaker.Faker;
import org.ants.opportunity.model.Type;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class TypeLoader {
    private static final MongoTemplate DATABASE = DatabaseConnection.getIntance();
    private static final Faker FAKER = new Faker();
    private static final String TYPE_COLLECTION = "type";

    public static void addFakeElement(int numberOfTypes){
        for(int i = 0;i < numberOfTypes; i++) {
            DATABASE.save(createFakeElement(), TYPE_COLLECTION);
        }
    }

    public static void addElement(Type type){
        DATABASE.save(type, TYPE_COLLECTION);
    }

    public static Type getFakeElement(){
        Type type = createFakeElement();
        DATABASE.insert(type);
        return type;
    }

    public static void removeElement(Type type){
        DATABASE.remove(type);
    }

    public static void removeAllElements(){
        DATABASE.remove(new Query(),TYPE_COLLECTION);
    }

    private static Type createFakeElement(){
        Type type = new Type();
        type.setName(FAKER.funnyName().name());
        return type;
    }

}
