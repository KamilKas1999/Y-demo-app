package com.kasprzak.kamil.demoapp.event.service;

import com.kasprzak.kamil.demoapp.event.EventEntity;
import com.kasprzak.kamil.demoapp.notification.NotificationEntity;

import java.util.List;

public interface EventService {

    void createEvent(Long userId, String content);

    List<EventEntity> getEvents(final Long userId);
}
