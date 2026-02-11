package com.kasprzak.kamil.demoapp.notification.command.updateStatus;

import com.kasprzak.kamil.demoapp.common.command.Command;

public record UpdateStatusNotificationCommand(
        long id
) implements Command {}
