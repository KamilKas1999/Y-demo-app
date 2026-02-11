package com.kasprzak.kamil.demoapp.notification.command.updateStatus;

import com.kasprzak.kamil.demoapp.notification.exceptions.NotificationNotFoundException;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateNotificationCommandHandlerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UpdateNotificationCommandHandler handler;

    @Test
    void shouldUpdateStatus() throws Exception {
        // given
        Long notificationId = 1L;
        UpdateStatusNotificationCommand command = new UpdateStatusNotificationCommand(notificationId);

        // when
        handler.handle(command);

        // then
        verify(notificationService, times(1)).updateStatus(notificationId);
    }

    @Test
    void shouldThrowNotificationNotFoundException() throws Exception {
        // given
        Long notificationId = 99L;
        UpdateStatusNotificationCommand command = new UpdateStatusNotificationCommand(notificationId);

        doThrow(new NotificationNotFoundException())
                .when(notificationService).updateStatus(notificationId);

        // when + then
        NotificationNotFoundException exception = assertThrows(NotificationNotFoundException.class, () -> {
            handler.handle(command);
        });

        assertFalse(exception.getMessage().isEmpty());
        verify(notificationService, times(1)).updateStatus(notificationId);
    }
}