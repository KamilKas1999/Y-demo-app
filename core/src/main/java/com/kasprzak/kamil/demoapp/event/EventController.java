package com.kasprzak.kamil.demoapp.event;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.event.mapper.EventsQueryResultToEventDTOMapper;
import com.kasprzak.kamil.demoapp.event.query.get.EventsQuery;
import com.kasprzak.kamil.demoapp.event.query.get.EventsQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final QueryExecutor queryExecutor;

    private final CommandExecutor commandExecutor;

    private final EventsQueryResultToEventDTOMapper mapper;

    @GetMapping("/{userId}")
    public EventsResponse getEvents(@PathVariable Long userId) throws BusinesException {
        var queryResult = queryExecutor.execute(new EventsQuery(userId), EventsQueryResult.class);
        return mapper.map(queryResult);
    }
}
