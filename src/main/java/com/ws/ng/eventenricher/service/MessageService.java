package com.ws.ng.eventenricher.service;

import com.ws.ng.eventenricher.model.Message;
import com.ws.ng.eventenricher.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value(value = "${kafka.topic.enrichment.success}")
    private String enrichmentSuccessEventTopic;

    @Value(value = "${kafka.topic.enrichment.failure}")
    private String enrichmentFailureEventTopic;

    @Value(value = "${kafka.topic.enrichment.enrichment}")
    private String enrichmentEventTopic;


    @Autowired
    private MessageProducer messageProducer;

    public void sendMessage(Message message){
        messageProducer.produce(getTopic(message), message);
    }

    private String getTopic(Message message){
        switch (message.getPayload())
        {
            case "success":
                return enrichmentSuccessEventTopic;
            case "error":
                return enrichmentFailureEventTopic;
            default:
                return enrichmentEventTopic;
        }
    }

}
