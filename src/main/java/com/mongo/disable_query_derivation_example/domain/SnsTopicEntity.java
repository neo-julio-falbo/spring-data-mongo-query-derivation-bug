package com.mongo.disable_query_derivation_example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "sns_topics")
public class SnsTopicEntity {
    @Id
    private String id;
    private String name;
    private String arn;
    private List<String> subscriptions;

    public SnsTopicEntity() {
    }

    public SnsTopicEntity(String arn) {
        this.arn = arn;
    }

    public SnsTopicEntity(String arn, String name) {
        this.arn = arn;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String serviceName) {
        this.name = serviceName;
    }
}