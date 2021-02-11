package ru.vlapin.demo.kafkademo.producer;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.vlapin.demo.kafkademo.producer.config.MyKafkaProperties;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class KafkaDemoApplication {

  MyKafkaProperties kafkaProperties;

  public static void main(String[] args) {
    SpringApplication.run(KafkaDemoApplication.class, args);
  }

  @Bean
  KafkaAdmin kafkaAdmin() {
    val config = Map.<String, Object>of(
        BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapAddress());
    return new KafkaAdmin(config);
  }

  @Bean
  NewTopic newTopic() {
    return new NewTopic(kafkaProperties.getTopicName(), 1, (short) 1);
  }

  @Bean
  KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  ProducerFactory<String, String> producerFactory() {
    val config = Map.<String, Object>of(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapAddress(),
        KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
        VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
    );
    return new DefaultKafkaProducerFactory<>(config);
  }
}
