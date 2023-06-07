package com.bridgingcode.springbootactivemqdemo.publisher.controller;

import com.bridgingcode.springbootactivemqdemo.model.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Topic;


@RestController
public class PublishController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Topic topic;

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody SystemMessage systemMessage) {
        try {
            jmsTemplate.convertAndSend("andreneckel-queue", systemMessage);
            return new ResponseEntity<>("Sent.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/publishTopc")
    public ResponseEntity<String> publishTopic(@RequestBody String message) {
        try {
            jmsTemplate.convertAndSend(topic, message); // Envie a mensagem para o tópico
            return new ResponseEntity<>("Sent topic.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
