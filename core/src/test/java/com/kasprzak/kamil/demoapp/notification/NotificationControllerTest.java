package com.kasprzak.kamil.demoapp.notification;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.notification.command.create.CreateNotificationCommand;
import com.kasprzak.kamil.demoapp.notification.command.create.CreateNotificationResult;
import com.kasprzak.kamil.demoapp.notification.command.updateStatus.UpdateStatusNotificationCommand;
import com.kasprzak.kamil.demoapp.notification.mapper.toNotificationDTOMapper;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQuery;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQueryResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private QueryExecutor queryExecutor;

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private toNotificationDTOMapper mapper;

    @InjectMocks
    private NotificationController controller;

    @Test
    void testGetNotification() throws BusinesException {
        //given
        Long userId = 1L;
        int page = 0;
        int size = 20;

        NotificationsQueryResult queryResult = mock(NotificationsQueryResult.class);
        List<NotificationDTO> notifications = List.of(new NotificationDTO(1L, "topic", "content"));

        when(queryExecutor.execute(any(), any())).thenReturn(queryResult);
        when(mapper.map(queryResult)).thenReturn(new NotificationsResponse(notifications));

        //when
        List<NotificationDTO> result = controller.getNotification(userId, page, size);

        // then
        assertEquals(1, result.size());
        assertEquals("topic", result.get(0).topic());

        verify(queryExecutor).execute(any(NotificationsQuery.class), eq(NotificationsQueryResult.class));
        verify(mapper).map(queryResult);
    }

    @Test
    void testCreateNotification() throws BusinesException {
        // given
        Long userId = 1L;
        CreateNotificationRequest request = new CreateNotificationRequest("topic", "content");
        CreateNotificationResult resultMock = new CreateNotificationResult(42L);

        when(commandExecutor.execute(any(), any())).thenReturn(resultMock);

        // when
        ResponseEntity<CreateNotificationResponse> response = controller.createNotification(userId, request);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(42L, response.getBody().id());

        verify(commandExecutor).execute(any(CreateNotificationCommand.class), eq(CreateNotificationResult.class));
    }

    @Test
    void testUpdateNotificationStatus() throws BusinesException {
        // given
        Long notificationId = 99L;

        //when
        controller.updateNotificationStatus(notificationId);

        // then
        verify(commandExecutor).execute(any(UpdateStatusNotificationCommand.class));
    }

}