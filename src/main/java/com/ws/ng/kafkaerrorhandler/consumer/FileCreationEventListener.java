package com.ws.ng.kafkaerrorhandler.consumer;

import com.ws.ng.kafkaerrorhandler.model.IcdEventFileCreation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FileCreationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCreationEventListener.class);


    @KafkaListener( topics = "${kafka.topic.name.file-creation}",
			        groupId = "#{'${spring.kafka.consumer.group-id}'.concat('-log')}",
			        containerFactory = "kafkaListenerContainerFactory")
	public void listenAndLog(ConsumerRecord<String, IcdEventFileCreation> cr, @Payload IcdEventFileCreation message) {
        LOGGER.info("listenAndLog, Consume message: '{}'", message.toString());
    }

    @KafkaListener( topics = "${kafka.topic.name.file-creation}",
            groupId = "#{'${spring.kafka.consumer.group-id}'.concat('-error')}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenWithException(ConsumerRecord<String,  IcdEventFileCreation> cr, @Payload IcdEventFileCreation message) {

        LOGGER.info("listenWithException, Consume message: '{}'", message.toString());
        throw new RuntimeException("Runtime exception");
    }

    @KafkaListener( topics = "#{'${kafka.topic.name.file-creation}'.concat('.DLT')}",
            groupId = "#{'${spring.kafka.consumer.group-id}'.concat('-error-DLT')}",
            containerFactory = "kafkaListenerContainerDLFactory")
    public void listenWithExceptionDLT(ConsumerRecord<String, IcdEventFileCreation> cr, @Payload IcdEventFileCreation message) {
        LOGGER.info("listenWithException, Consume message: '{}'", message.toString());
    }


}
