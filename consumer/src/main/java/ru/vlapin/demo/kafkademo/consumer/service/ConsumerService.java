package ru.vlapin.demo.kafkademo.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsumerService {

  @KafkaListener(
      topics = "${mykafka.topic-name}",
      groupId = "${spring.kafka.consumer.group-id}")
  public void consumeMessages(String message) {
    log.info("CONSUMER: We received a message!!! {}", message);
  }
}
