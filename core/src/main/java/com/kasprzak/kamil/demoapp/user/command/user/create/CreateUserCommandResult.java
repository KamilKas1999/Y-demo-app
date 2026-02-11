package com.kasprzak.kamil.demoapp.user.command.user.create;

import com.kasprzak.kamil.demoapp.common.command.CommandResult;

public record CreateUserCommandResult(
        Long userId
) implements CommandResult {
}
