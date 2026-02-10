package com.kasprzak.kamil.demoapp.notification.service;

import com.kasprzak.kamil.demoapp.notification.NotificationEntity;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService{

    void createNotification(Long userId, String topic, String content) throws UserNotFoundException;

    Page<NotificationEntity> getNotifications(final Long userId, final Pageable pageable);
}
