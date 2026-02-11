package com.kasprzak.kamil.demoapp.event.query.get;

import com.kasprzak.kamil.demoapp.common.query.QueryHandler;
import com.kasprzak.kamil.demoapp.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventsQueryHandler implements QueryHandler<EventsQuery, EventsQueryResult> {

    private final EventService eventService;

    public EventsQueryResult handle(EventsQuery query) {
        var events = eventService.getEvents(query.userId());
        return EventsQueryResult.builder()
                .events(events)
                .build();
    }

}
