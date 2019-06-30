package com.ws.ng.eventenricher.config;


import com.ws.ng.loginfra.kafka.KafkaParams;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaTopicConfig {

    @Autowired
    private KafkaParams kafkaParams;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic.enrichment.success}")
    private String enrichmentSuccessEventTopic;

    @Value(value = "${kafka.topic.enrichment.failure}")
    private String enrichmentFailureEventTopic;

    @Value(value = "${kafka.topic.enrichment.enrichment}")
    private String enrichmentEventTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> propConfig = kafkaParams.consumerConfigs();
        propConfig.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(propConfig);
    }

    @Bean
    public NewTopic enrichmentSuccessEventTopic() {
        return new NewTopic(enrichmentSuccessEventTopic, 3, (short) 1);
    }

    @Bean
    public NewTopic enrichmentFailureEventTopic() {
        return new NewTopic(enrichmentFailureEventTopic, 3, (short) 1);
    }
    @Bean
    public NewTopic enrichmentEventTopic() {
        return new NewTopic(enrichmentEventTopic, 3, (short) 1);
    }

}
