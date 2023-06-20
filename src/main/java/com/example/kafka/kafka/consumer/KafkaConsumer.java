package com.example.kafka.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {
    Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;
    @KafkaListener(topics = "${test.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String consumerRecord) {
        payload = consumerRecord;
        LOGGER.info("Received '{}' Message with latch: {}", payload, latch);
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}
