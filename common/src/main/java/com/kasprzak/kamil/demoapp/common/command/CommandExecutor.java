package com.kasprzak.kamil.demoapp.common.command;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;

public interface CommandExecutor {

    void execute(Command command) throws CommandHandlerNotFoundExeption, BusinesException;

    <T extends CommandResult> T execute(Command command, Class<T> resultType) throws CommandHandlerNotFoundExeption, BusinesException;
}
