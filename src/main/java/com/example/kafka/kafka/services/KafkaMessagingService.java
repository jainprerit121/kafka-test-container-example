package com.example.kafka.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagingService {

    Logger LOGGER = LoggerFactory.getLogger(KafkaMessagingService.class);

    @Value("${test.topic}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        sendAndWaitToComplete(message, topicName);
    }

    private void sendAndWaitToComplete(String message, String topicName) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.whenComplete((result, ex) ->{
            if (ex == null) {
                LOGGER.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                LOGGER.info("Unable to send message=[{}}] due to : {}", message, ex.getMessage());
            }
        });
    }

    public void sendMessage(String topic, String message) {
        LOGGER.info("sending payload='{}' to topic='{}'", message, topic);
        sendAndWaitToComplete(message, topic);
    }
}
