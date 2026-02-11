package com.kasprzak.kamil.demoapp.notification;

import lombok.Builder;

@Builder
public record CreateNotificationRequest(
        String topic,
        String content
) {
}
