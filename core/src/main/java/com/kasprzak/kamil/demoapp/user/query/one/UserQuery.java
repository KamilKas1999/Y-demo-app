package com.kasprzak.kamil.demoapp.user.query.one;

import com.kasprzak.kamil.demoapp.common.query.Query;

public record UserQuery(
        Long userId
) implements Query<UsersQueryResult> {
}
