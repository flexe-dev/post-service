package com.flexe.postservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "user-post", groupId = "flexe-post-service")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}