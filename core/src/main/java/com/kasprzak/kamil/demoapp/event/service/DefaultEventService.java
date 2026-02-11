package com.kasprzak.kamil.demoapp.event.service;

import com.kasprzak.kamil.demoapp.event.EventEntity;
import com.kasprzak.kamil.demoapp.event.EventRepository;
import com.kasprzak.kamil.demoapp.notification.NotificationEntity;
import com.kasprzak.kamil.demoapp.notification.NotificationRepository;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultEventService implements EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public void createEvent(Long userId, String content) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        var entity = EventEntity
                .builder()
                .user(user)
                .content(content)
                .build();
        
        eventRepository.save(entity);
    }


    public List<EventEntity> getEvents(final Long userId){
        return eventRepository.findByUserId(userId);
    }

}
