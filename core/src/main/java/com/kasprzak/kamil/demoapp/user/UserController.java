package com.kasprzak.kamil.demoapp.user;

import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.user.command.user.delete.DeleteUserCommand;
import com.kasprzak.kamil.demoapp.user.query.all.UsersListQuery;
import com.kasprzak.kamil.demoapp.user.query.all.UsersListQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private QueryExecutor queryExecutor;

    @Autowired
    private CommandExecutor commandExecutor;

    @GetMapping
    public UsersResponse getUsers() throws BusinesException {
        var query = new UsersListQuery();
        var result = queryExecutor.execute(query, UsersListQueryResult.class);
        return UsersResponse
                .builder()
                .users(result.getUserEntities()
                        .stream()
                        .map(userEntity -> UserDTO
                                .builder()
                                .id(userEntity.getId())
                                .email(userEntity.getEmail())
                                .firstname(userEntity.getFirstname())
                                .lastname(userEntity.getLastname())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @DeleteMapping
    public void deleteUser(final DeleteUserRequest userDTO) throws BusinesException {
        var command = new DeleteUserCommand(userDTO.getUserId());
        commandExecutor.execute(command);
    }
}
