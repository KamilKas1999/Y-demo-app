package com.kasprzak.kamil.demoapp.user.query.all;

import com.kasprzak.kamil.demoapp.common.query.QueryHandler;
import com.kasprzak.kamil.demoapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersListQueryHandler implements QueryHandler<UsersListQuery,UsersListQueryResult> {

    private final UserService userService;

    public UsersListQueryResult handle(UsersListQuery query){
        var users =  userService.getUsers();
        return UsersListQueryResult.builder()
                .userEntities(users)
                .build();
    }

}
