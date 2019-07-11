package com.ws.ng.kafkaerrorhandler.config;

import com.ws.ng.infra.kafka.config.WsJsonDeserializer;
import com.ws.ng.kafkaerrorhandler.model.Message;
import com.ws.ng.kafkaerrorhandler.producer.MessageProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }


  @Bean
  public Map<String, Object> appProducerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return props;
  }

  @Bean
    public ProducerFactory<String, Message> messageProducerFactory() {
      return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Message> messageKafkaTemplate() {
      return new KafkaTemplate<>(messageProducerFactory());
    }


    @Bean
    public ProducerFactory<Object, Object> appProducerFactory() {
      return new DefaultKafkaProducerFactory<>(appProducerConfigs());
    }

    @Bean
    public KafkaTemplate<Object, Object> appKafkaTemplate() {
      return new KafkaTemplate<>(appProducerFactory());
    }

    @Bean
    public MessageProducer messageProducer(){
        return new MessageProducer();
      }


    @Bean
    public SeekToCurrentErrorHandler seekToCurrentErrorHandlerDeadLetter(){
	BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition>
		DEFAULT_DESTINATION_RESOLVER = (cr, e) -> {
	    if (e instanceof IOException)
	        return  new TopicPartition(cr.topic() + ".DLT", cr.partition());
	    else if (e instanceof KafkaException)
	        return  new TopicPartition(cr.topic() + ".DLT", cr.partition());
	    else
            return new TopicPartition(cr.topic() + ".DLT-stop", cr.partition());
	};


      DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(appKafkaTemplate(), DEFAULT_DESTINATION_RESOLVER);
      return new SeekToCurrentErrorHandler(
              deadLetterPublishingRecoverer,
                      3);
    }

    //By default, the error handler tracks the failed record, gives up after 10 delivery attempts
    //and logs the failed record
    @Bean
    public SeekToCurrentErrorHandler seekToCurrentErrorHandler(){

      return new SeekToCurrentErrorHandler();
    }

}
