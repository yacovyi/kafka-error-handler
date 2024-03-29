package com.ws.ng.kafkaerrorhandler.config;

import com.ws.ng.infra.kafka.config.WsJsonDeserializer;
import com.ws.ng.kafkaerrorhandler.model.IcdEventFileCreation;
import com.ws.ng.loginfra.kafka.KafkaParams;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    @Autowired
    private KafkaParams kafkaParams;

    @Autowired
    private SeekToCurrentErrorHandler seekToCurrentErrorHandler;

    @Autowired
    private SeekToCurrentErrorHandler seekToCurrentErrorHandlerDeadLetter;


    @Bean
    public ConsumerFactory<String, IcdEventFileCreation> consumerFactory() {
        JsonDeserializer<IcdEventFileCreation> jsonDeserializer =
                new WsJsonDeserializer<>(IcdEventFileCreation.class);

        return new DefaultKafkaConsumerFactory<>(
                kafkaParams.consumerConfigs(),
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConsumerFactory<String, IcdEventFileCreation> consumerDLFactory() {


        return new DefaultKafkaConsumerFactory<>(
                kafkaParams.consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(IcdEventFileCreation.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IcdEventFileCreation> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IcdEventFileCreation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        //factory.setErrorHandler(seekToCurrentErrorHandler);
        factory.setErrorHandler(seekToCurrentErrorHandlerDeadLetter);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IcdEventFileCreation> kafkaListenerContainerDLFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IcdEventFileCreation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerDLFactory());

        factory.setErrorHandler(seekToCurrentErrorHandler);
        return factory;
    }




}
