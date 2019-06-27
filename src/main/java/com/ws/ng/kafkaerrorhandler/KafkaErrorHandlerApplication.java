package com.ws.ng.kafkaerrorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {
         "com.ws.ng.infra.kafka.config",
        "com.ws.ng.loginfra.kafka",
        "com.ws.ng.kafkaerrorhandler"
})
@EntityScan(basePackages = "com.ws.ng.kafkaerrorhandler.model")
public class KafkaErrorHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaErrorHandlerApplication.class, args);
    }

}
