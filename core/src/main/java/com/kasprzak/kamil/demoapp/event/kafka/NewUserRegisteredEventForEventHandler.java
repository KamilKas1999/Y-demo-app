package com.kasprzak.kamil.demoapp.event.kafka;

import com.kasprzak.kamil.demoapp.auth.event.NewUserRegisteredEvent;
import com.kasprzak.kamil.demoapp.common.kafka.consumer.KafkaMessageHandler;
import com.kasprzak.kamil.demoapp.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NewUserRegisteredEventForEventHandler implements KafkaMessageHandler<NewUserRegisteredEvent> {

    private final EventService eventService;


    @Override
    public boolean supports(Class<?> eventType) {
        return NewUserRegisteredEvent.class.equals(eventType);
    }

   @Override
    public void handle(NewUserRegisteredEvent event) {
        System.out.println("Event received");
        eventService.createEvent(event.getUserId(), "User registered " + LocalDate.now());
    }

    @Override
    public Class<NewUserRegisteredEvent> getEventType() {
        return NewUserRegisteredEvent.class;
    }
}