package com.kasprzak.kamil.demoapp.user.command.user.delete;

import com.kasprzak.kamil.demoapp.common.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteUserCommand implements Command {

      private long id;
}
