package com.jnwee.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoDbDataAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration(
    exclude = {
        MongoAutoConfiguration.class, MongoDbDataAutoConfiguration.class,
    }
)
class BackendApplicationTests {

    @Test
    void contextLoads() {}
}
