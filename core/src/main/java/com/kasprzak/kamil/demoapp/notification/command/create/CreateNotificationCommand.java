package com.kasprzak.kamil.demoapp.notification.command.create;

import com.kasprzak.kamil.demoapp.common.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

public record CreateNotificationCommand(
        long userId,
        String topic,
        String content
) implements Command {}
