package ru.vlapin.demo.kafkademo.producer.service;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
//@Service
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("mykafka")
public class KafkaProducerService {

  @NonFinal
  @Setter(value = PRIVATE, onMethod_ = @Autowired)
  KafkaTemplate<String, String> kafkaTemplate;

  //  @Value("${mykafka.topic-name}")
  String topicName;

  private static int runningId;

  @Scheduled(
      fixedRate = 2 * 1_000,
      initialDelay = 2 * 1_000)
  public final void produceMessages() {

    log.info("Produce message - BEGIN");

    val message =
        "Hello %d, this is a kafka message %s "
            .formatted(
                runningId++,
                LocalDateTime.now());

    val producerRecord = new ProducerRecord<String, String>(topicName, message);

//    kafkaTemplate.send(topicName, message)
    kafkaTemplate.send(producerRecord)
        .addCallback(
            result -> log.info("SUCCESS!!! This is the result: {}", result),
            ex -> log.error("ERROR Kafka error happened: {}", ex.toString()));

    val producerRecord2 = new ProducerRecord<>(
        topicName,
        "Precision Products",
        "France ++");

    kafkaTemplate.send(producerRecord2)
        .addCallback(
            result -> log.info("SUCCESS!!! This is the result: {}", result),
            ex -> log.error("ERROR Kafka error happened: {}", ex.toString()));

    log.info("Produce message - END {}", message);
  }
}
