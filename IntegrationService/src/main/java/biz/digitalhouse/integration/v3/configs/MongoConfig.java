package biz.digitalhouse.integration.v3.configs;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

/**
 * @author Vitalii Babenko
 *         on 25.07.2016.
 */
@Configuration
public class MongoConfig {

    @Value("${mongo.db.replica}")
    private String replica;
    @Value("${mongo.db.database}")
    private String dbname;

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        System.out.println("#########################################################");
        System.out.println("mongo.db.replica: " + replica);
        System.out.println("mongo.db. URL: " + "mongodb://" + replica + "/" + dbname);
        System.out.println("#########################################################");
        System.out.println("#########################################################");
        return new MongoTemplate(new SimpleMongoDbFactory(new MongoClientURI("mongodb://" + replica + ":27017/" + dbname)));
    }
}
