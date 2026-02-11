package com.kasprzak.kamil.demoapp.common.command;


import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;

public interface CommandHandlerWithResult<C extends Command, R extends CommandResult> {
    R handle(C command) throws BusinesException;
}
