package com.example.kafka.kafka.controllers;

import com.example.kafka.kafka.services.KafkaMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class KafkaMessagingController {
    @Autowired
    private KafkaMessagingService kafkaMessagingService;

    @PostMapping("/publish")
    public ResponseEntity publishEvent(@RequestBody String message){
        kafkaMessagingService.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
