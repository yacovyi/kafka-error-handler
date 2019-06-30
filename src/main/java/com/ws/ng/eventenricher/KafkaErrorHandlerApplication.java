package com.ws.ng.eventenricher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {
         "com.ws.ng.infra.kafka.config",
        "com.ws.ng.loginfra.kafka",
        "com.ws.ng.eventenricher"
})
@EntityScan(basePackages = "com.ws.ng.eventenricher.model")
public class KafkaErrorHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaErrorHandlerApplication.class, args);
    }

}
