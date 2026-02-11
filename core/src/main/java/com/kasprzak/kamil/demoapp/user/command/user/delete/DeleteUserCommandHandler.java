package com.kasprzak.kamil.demoapp.user.command.user.delete;

import com.kasprzak.kamil.demoapp.user.service.UserService;
import com.kasprzak.kamil.demoapp.common.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand> {

    private final UserService userService;

    @Override
    public void handle(DeleteUserCommand command) {
       userService.deleteUser(command.getId());
    }
}
