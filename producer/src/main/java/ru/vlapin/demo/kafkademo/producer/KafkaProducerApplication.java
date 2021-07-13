package ru.vlapin.demo.kafkademo.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class KafkaProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerApplication.class, args);
  }

  @Bean
  NewTopic newTopic(@Value("${mykafka.topic-name}") String topicName) {
    return TopicBuilder
               .name(topicName)
//               .partitions(1)
//               .replicas(1)
               .build();
//    return new NewTopic(topicName, 1, (short) 1);
  }
}
