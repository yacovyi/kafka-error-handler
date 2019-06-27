package com.ws.ng.kafkaerrorhandler.producer;

import com.ws.ng.kafkaerrorhandler.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class MessageProducer {
     private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

     @Autowired
     private KafkaTemplate<String, Message> messageKafkaTemplate;

     public void produce(String topic, Message message){
         LOGGER.info("Produce message='{}'", message.toString());
         messageKafkaTemplate.send(topic, message);
     }
}
