package com.kasprzak.kamil.demoapp.common.kafka.consumer;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaMessageHandler<T> {
    boolean supports(Class<?> eventType);
    void handle(T event) throws BusinesException;
    Class<T> getEventType();
}
