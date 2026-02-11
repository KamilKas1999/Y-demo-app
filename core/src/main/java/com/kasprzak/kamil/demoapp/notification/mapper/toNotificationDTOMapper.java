package com.kasprzak.kamil.demoapp.notification.mapper;

import com.kasprzak.kamil.demoapp.notification.NotificationDTO;
import com.kasprzak.kamil.demoapp.notification.NotificationsResponse;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQueryResult;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class toNotificationDTOMapper {


    public NotificationsResponse map(NotificationsQueryResult source) {
        return NotificationsResponse
                .builder()
                .notifications(source.getNotifications().stream()
                        .map(notificationEntity -> NotificationDTO
                                .builder()
                                .id(notificationEntity.getId())
                                .topic(notificationEntity.getTopic())
                                .content(notificationEntity.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
