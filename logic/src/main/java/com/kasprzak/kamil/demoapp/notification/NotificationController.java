package com.kasprzak.kamil.demoapp.notification;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.notification.mapper.NotificationsQueryResultToNotificationDTOMapper;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQuery;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private QueryExecutor queryExecutor;

    private CommandExecutor commandExecutor;

    private final NotificationsQueryResultToNotificationDTOMapper mapper;

    @GetMapping("/{userId}")
    public NotificationsRequest getNotification(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) throws BusinesException {

        var queryResult = queryExecutor.execute(
                new NotificationsQuery(userId, page, size),
                NotificationsQueryResult.class
        );

        return mapper.map(queryResult);
    }

}
