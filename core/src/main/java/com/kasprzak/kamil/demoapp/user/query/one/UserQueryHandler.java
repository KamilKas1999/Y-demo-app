package com.kasprzak.kamil.demoapp.user.query.one;

import com.kasprzak.kamil.demoapp.common.query.QueryHandler;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import com.kasprzak.kamil.demoapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQueryHandler implements QueryHandler<UserQuery, UsersQueryResult> {

    private final UserService userService;

    public UsersQueryResult handle(UserQuery query) throws UserNotFoundException {
        var user = userService.getUsersById(query.userId());
        return UsersQueryResult.builder()
                .userEntities(user)
                .build();
    }

}
