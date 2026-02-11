package com.kasprzak.kamil.demoapp.notification.command.create;

import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNotificationCommandHandlerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CreateNotificationCommandHandler handler;

    @Test
    void shouldReturnNotificationId() throws Exception {
        // given
        Long userId = 1L;
        String topic = "Test Topic";
        String content = "Test Content";
        Long expectedId = 42L;

        CreateNotificationCommand command = new CreateNotificationCommand(userId, topic, content);

        when(notificationService.createNotification(userId, topic, content)).thenReturn(expectedId);

        // when
        CreateNotificationResult result = handler.handle(command);

        // then
        assertNotNull(result);
        assertEquals(expectedId, result.notificationId());

        verify(notificationService).createNotification(userId, topic, content);
    }

    @Test
    void shouldThrowUserNotFoundException() throws Exception {
        // given
        Long userId = 99L;
        String topic = "Topic";
        String content = "Content";

        CreateNotificationCommand command = new CreateNotificationCommand(userId, topic, content);

        when(notificationService.createNotification(userId, topic, content))
                .thenThrow(new UserNotFoundException(userId));

        // when + then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            handler.handle(command);
        });

        assertFalse(exception.getMessage().isEmpty());
        verify(notificationService).createNotification(userId, topic, content);
    }
}