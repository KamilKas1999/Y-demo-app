package com.kasprzak.kamil.demoapp.notification;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.mapper.MapperExecutor;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.notification.mapper.NotificationsQueryResultToNotificationDTOMapper;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQuery;
import com.kasprzak.kamil.demoapp.notification.query.get.NotificationsQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private QueryExecutor queryExecutor;

    private CommandExecutor commandExecutor;

    private final NotificationsQueryResultToNotificationDTOMapper mapper;

    @GetMapping("/{userId}")
    public NotificationsRequest getNotification(@PathVariable Long userId) throws BusinesException {
        var queryResult = queryExecutor.execute(new NotificationsQuery(userId), NotificationsQueryResult.class);
        return mapper.map(queryResult);
    }

}
