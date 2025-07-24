package com.mongo.disable_query_derivation_example.domain.repositories;

import com.mongo.disable_query_derivation_example.domain.SnsTopicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnsTopicRepository extends MongoRepository<SnsTopicEntity, String>, CustomSnsRepository<SnsTopicEntity> {
}