package com.kasprzak.kamil.demoapp.notification.command.create;

import com.kasprzak.kamil.demoapp.common.command.CommandHandlerWithResult;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateNotificationCommandHandler implements CommandHandlerWithResult<CreateNotificationCommand, CreateNotificationResult> {

    private final NotificationService notificationService;

    @Override
    public CreateNotificationResult handle(CreateNotificationCommand command) throws UserNotFoundException {
        var id = notificationService.createNotification(command.userId(), command.topic(), command.content());
        return CreateNotificationResult.builder()
                .notificationId(id)
                .build();
    }
}
