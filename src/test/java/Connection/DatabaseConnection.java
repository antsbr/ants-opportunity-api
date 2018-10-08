package Connection;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

public class DatabaseConnection {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 27017;
    private static MongodExecutable mongoInstance;
    private static final MongoTemplate mongoTemplate = initializeConnection();

    public static MongoTemplate getIntance(){
        return mongoTemplate;
    }

    private static MongoTemplate initializeConnection(){
        return new MongoTemplate(new MongoClient(IP, PORT), "ants");
    }

    public void startLocalDatabase(){
        try {
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                    .build();

            MongodStarter starter = MongodStarter.getDefaultInstance();
            mongoInstance = starter.prepare(mongodConfig);
            mongoInstance.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopLocalDatabase(){
        mongoInstance.stop();
    }
}
