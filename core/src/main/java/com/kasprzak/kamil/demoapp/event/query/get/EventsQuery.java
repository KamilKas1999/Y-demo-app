package com.kasprzak.kamil.demoapp.event.query.get;

import com.kasprzak.kamil.demoapp.common.query.Query;

public record EventsQuery(
        Long userId
) implements Query<EventsQueryResult> {
}
