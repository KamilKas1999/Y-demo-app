package com.kasprzak.kamil.demoapp.notification.query.get;

import com.kasprzak.kamil.demoapp.notification.NotificationDTO;
import com.kasprzak.kamil.demoapp.notification.NotificationEntity;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationsQueryHandlerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationsQueryHandler handler;

    @Test
    void shouldReturnNotifications() {
        // given
        Long userId = 1L;
        int page = 0;
        int size = 10;

        NotificationsQuery query = new NotificationsQuery(userId, page, size);

        List<NotificationEntity> entities = List.of(
                new NotificationEntity(1L, mock(UserEntity.class), "Topic 1", "Content 1", false, Instant.now()),
                new NotificationEntity(2L, mock(UserEntity.class), "Topic 2", "Content 2", false, Instant.now())
        );

        Page<NotificationEntity> pageResult = new PageImpl<>(entities);

        when(notificationService.getNotifications(eq(userId), any(PageRequest.class)))
                .thenReturn(pageResult);

        // when
        NotificationsQueryResult result = handler.handle(query);

        // then
        assertNotNull(result);
        assertEquals(2, result.getNotifications().getSize());
        assertEquals("Topic 1", result.getNotifications().getContent().get(0).getTopic());
        assertEquals("Topic 2", result.getNotifications().getContent().get(1).getTopic());

        verify(notificationService).getNotifications(eq(userId), any(PageRequest.class));
    }
}