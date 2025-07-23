package com.mongo.disable_query_derivation_example;

import com.mongo.disable_query_derivation_example.domain.SnsTopicEntity;
import com.mongo.disable_query_derivation_example.domain.repositories.SnsTopicRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap {
    private final SnsTopicRepository snsTopicRepository;

    public Bootstrap(SnsTopicRepository snsTopicRepository) {
        this.snsTopicRepository = snsTopicRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadOnStartup() {
        snsTopicRepository.save(new SnsTopicEntity("arn:aws:sns:us-east-1:123456789012:MyTopic", "MyTopic"));
    }
}
