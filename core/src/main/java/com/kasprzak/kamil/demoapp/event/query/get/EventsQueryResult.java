package com.kasprzak.kamil.demoapp.event.query.get;

import com.kasprzak.kamil.demoapp.common.query.QueryResult;
import com.kasprzak.kamil.demoapp.event.EventEntity;
import com.kasprzak.kamil.demoapp.notification.NotificationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class EventsQueryResult implements QueryResult {

    List<EventEntity> events;
}
