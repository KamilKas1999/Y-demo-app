package com.kasprzak.kamil.demoapp.common.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducer<T> {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEvent(final T event) {
        try {
            var eventType = event.getClass().getName();
            var eventValue = objectMapper.writeValueAsString(event);
            var record = new ProducerRecord<>("topic", UUID.randomUUID().toString(), eventValue);
            record.headers().add(new RecordHeader("event-type", eventType.getBytes(StandardCharsets.UTF_8)));

            kafkaTemplate.send(record)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                        } else {
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send event", e);
        }
    }
}
