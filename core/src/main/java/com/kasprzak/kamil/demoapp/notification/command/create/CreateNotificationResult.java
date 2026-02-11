package com.kasprzak.kamil.demoapp.notification.command.create;

import com.kasprzak.kamil.demoapp.common.command.CommandResult;
import lombok.Builder;

@Builder
public record CreateNotificationResult(long notificationId) implements CommandResult {
}
