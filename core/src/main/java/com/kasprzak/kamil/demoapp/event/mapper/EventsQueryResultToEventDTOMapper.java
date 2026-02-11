package com.kasprzak.kamil.demoapp.event.mapper;

import com.kasprzak.kamil.demoapp.event.Event;
import com.kasprzak.kamil.demoapp.event.EventsResponse;
import com.kasprzak.kamil.demoapp.event.query.get.EventsQueryResult;

import java.util.stream.Collectors;

public class EventsQueryResultToEventDTOMapper{

    public EventsResponse map(EventsQueryResult source) {
        return EventsResponse
                .builder()
                .events(source.getEvents().stream()
                        .map(eventEntity -> Event
                                .builder()
                                .id(eventEntity.getId())
                                .userId(eventEntity.getUser().getId())
                                .content(eventEntity.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
