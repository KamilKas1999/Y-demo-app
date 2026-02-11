package com.kasprzak.kamil.demoapp.notification.service;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.notification.NotificationEntity;
import com.kasprzak.kamil.demoapp.notification.NotificationRepository;
import com.kasprzak.kamil.demoapp.notification.exceptions.NotificationNotFoundException;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService{

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public long createNotification(Long userId, String topic, String content) throws UserNotFoundException {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        NotificationEntity entity = NotificationEntity
                .builder()
                .user(user)
                .topic(topic)
                .content(content)
                .isRead(false)
                .createdAt(Instant.now())
                .build();

        return notificationRepository.save(entity).getId();

    }


    public Page<NotificationEntity> getNotifications(final Long userId, final Pageable pageable){
        return notificationRepository.findByUserId(userId, pageable);
    }

    public void updateStatus(long notificationId) throws NotificationNotFoundException {
        var notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException());

        notification.setRead(true);

        notificationRepository.save(notification);
    }

}
