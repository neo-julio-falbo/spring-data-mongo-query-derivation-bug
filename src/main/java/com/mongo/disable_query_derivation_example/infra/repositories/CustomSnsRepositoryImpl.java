package com.mongo.disable_query_derivation_example.infra.repositories;

import com.mongo.disable_query_derivation_example.domain.SnsTopicEntity;
import com.mongo.disable_query_derivation_example.domain.repositories.CustomSnsRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomSnsRepositoryImpl implements CustomSnsRepository {
    private final MongoTemplate mongoTemplate;
    
    public CustomSnsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    public boolean checkIfExistsByArn(String arn) {
        Query query = new Query(Criteria.where("arn").is(arn));
        return mongoTemplate.exists(query, SnsTopicEntity.class);
    }
}