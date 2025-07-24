package com.mongo.disable_query_derivation_example.domain.repositories;

public interface CustomSnsRepository<T> {
    boolean checkIfExistsByArn(String arn);  // Contains "By" keyword
}