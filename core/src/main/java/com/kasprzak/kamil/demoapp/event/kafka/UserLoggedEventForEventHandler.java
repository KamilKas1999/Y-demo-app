package com.kasprzak.kamil.demoapp.event.kafka;

import com.kasprzak.kamil.demoapp.auth.event.UserLoggedEvent;
import com.kasprzak.kamil.demoapp.common.kafka.consumer.KafkaMessageHandler;
import com.kasprzak.kamil.demoapp.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserLoggedEventForEventHandler implements KafkaMessageHandler<UserLoggedEvent> {

    private final EventService eventService;

    @Override
    public boolean supports(Class<?> eventType) {
        return UserLoggedEvent.class.equals(eventType);
    }

    @Override
    public void handle(UserLoggedEvent event) {
        System.out.println("Event received");
        eventService.createEvent(event.getUserId(), "User logged " + LocalDate.now());
    }

    @Override
    public Class<UserLoggedEvent> getEventType() {
        return UserLoggedEvent.class;
    }
}