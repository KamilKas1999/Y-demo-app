package com.kasprzak.kamil.demoapp.notification.command.create;

import com.kasprzak.kamil.demoapp.common.command.CommandHandler;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateNotificationCommandHandler implements CommandHandler<CreateNotificationCommand> {

    private final NotificationService notificationService;

    @Override
    public void handle(CreateNotificationCommand command) throws UserNotFoundException {
        notificationService.createNotification(command.userId(), command.topic(), command.content());
    }
}
