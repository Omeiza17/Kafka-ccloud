package dev.codingstoic.kafkaccloud.consumers;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    @KafkaListener(topics = {"hobbit"}, groupId = "hobbit-group")
    public void consume(ConsumerRecord<Integer, String> record) {
        var key = record.key();
        var value = record.value();
        log.info("Received key :: {} with message :: {}", key, value);
    }
}
