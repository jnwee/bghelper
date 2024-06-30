package com.project24.bghelper.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class GridFSConfig {

  private final MongoClient mongoClient;
  private final MongoTemplate mongoTemplate;

  public GridFSConfig(MongoClient mongoClient, MongoTemplate mongoTemplate) {
    this.mongoClient = mongoClient;
    this.mongoTemplate = mongoTemplate;
  }

  @Bean
  public GridFSBucket getGridFSBucket() {
    return GridFSBuckets.create(mongoClient.getDatabase(mongoTemplate.getDb().getName()));
  }
}
