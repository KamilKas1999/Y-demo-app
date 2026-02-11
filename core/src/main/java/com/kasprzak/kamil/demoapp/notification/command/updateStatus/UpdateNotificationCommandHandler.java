package com.kasprzak.kamil.demoapp.notification.command.updateStatus;

import com.kasprzak.kamil.demoapp.common.command.CommandHandler;
import com.kasprzak.kamil.demoapp.notification.exceptions.NotificationNotFoundException;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateNotificationCommandHandler implements CommandHandler<UpdateStatusNotificationCommand> {

    private final NotificationService notificationService;

    @Override
    public void handle(UpdateStatusNotificationCommand command) throws NotificationNotFoundException {
        notificationService.updateStatus(command.id());
    }
}
