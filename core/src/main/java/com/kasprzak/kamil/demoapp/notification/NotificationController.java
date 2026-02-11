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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final QueryExecutor queryExecutor;

    private final CommandExecutor commandExecutor;

    private final toNotificationDTOMapper mapper;

    @GetMapping("/{userId}")
    public List<NotificationDTO> getNotification(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) throws BusinesException {

        var queryResult = queryExecutor.execute(
                new NotificationsQuery(userId, page, size),
                NotificationsQueryResult.class
        );

        return mapper.map(queryResult).notifications();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CreateNotificationResponse> createNotification(
            @PathVariable Long userId,
            @RequestBody CreateNotificationRequest createNotificationRequest
    ) throws BusinesException {

        var command = new CreateNotificationCommand(userId, createNotificationRequest.topic(), createNotificationRequest.content());
        var result = commandExecutor.execute(command, CreateNotificationResult.class);

        var body = CreateNotificationResponse.builder()
                .id(result.notificationId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(body);
    }

    @PutMapping("/{notificationId}")
    public void updateNotificationStatus(
            @PathVariable Long notificationId
    ) throws BusinesException {
        var command = new UpdateStatusNotificationCommand(notificationId);
        commandExecutor.execute(command);
    }
}
