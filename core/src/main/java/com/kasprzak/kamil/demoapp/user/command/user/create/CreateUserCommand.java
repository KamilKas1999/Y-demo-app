package com.kasprzak.kamil.demoapp.user.command.user.create;

import com.kasprzak.kamil.demoapp.common.command.Command;
import com.kasprzak.kamil.demoapp.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand implements Command {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
