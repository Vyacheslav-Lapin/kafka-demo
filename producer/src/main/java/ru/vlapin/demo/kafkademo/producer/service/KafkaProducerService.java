package ru.vlapin.demo.kafkademo.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  KafkaTemplate kafkaTemplate;

  private static int runningId;

  @Scheduled(
      fixedRate = 10 * 1_000,
      initialDelay = 5 * 1_000)
  public final void produceMessages() {

    log.info("Produce message - BEGIN");

    val message =
        "Hello %d, this is a kafka message %s "
            .formatted(
                runningId++,
                LocalDateTime.now().toString());

    kafkaTemplate.send("mike", message);

    log.info("Produce message - END {}", message);
  }
}
