package com.kasprzak.kamil.demoapp.notification.query.get;

import com.kasprzak.kamil.demoapp.common.query.Query;
import org.springframework.data.domain.Pageable;

public record NotificationsQuery(
        Long userId,
        int page,
        int size
) implements Query<NotificationsQueryResult> {
}
