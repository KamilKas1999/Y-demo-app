package com.kasprzak.kamil.demoapp.common.command;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Supplier;


@Service
public class DefaultCommandExecutor implements CommandExecutor {

    @Autowired
    private List<CommandHandler<? extends Command>> commandHandlers;

    @Autowired
    private List<CommandHandlerWithResult<? extends Command, ? extends CommandResult>> commandHandlersWithResult;

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Command command) throws CommandHandlerNotFoundExeption, BusinesException {
        CommandHandler<Command> handler =
                (CommandHandler<Command>) commandHandlers.stream()
                        .filter(h -> isThisHandlerForThisCommand(command, h))
                        .findAny()
                        .orElseThrow(throwExeption(command));

        handler.handle(command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CommandResult> T execute(Command command, Class<T> resultType) throws CommandHandlerNotFoundExeption {
        CommandHandlerWithResult<Command, T> handler =
                (CommandHandlerWithResult<Command, T>) commandHandlersWithResult.stream()
                        .filter(h -> isThisHandlerForThisCommand(command, h))
                        .findAny()
                        .orElseThrow(throwExeption(command));

        return handler.handle(command);
    }

    private Supplier<CommandHandlerNotFoundExeption> throwExeption(Command command) {
        return () -> new CommandHandlerNotFoundExeption(command.getClass().getSimpleName());
    }

    private boolean isThisHandlerForThisCommand(Command command, CommandHandler<? extends Command> handler) {
        return handler.getClass()
                .getGenericInterfaces()[0] instanceof ParameterizedType &&
                command.getClass().equals(
                        (Class<?>) ((ParameterizedType) handler.getClass()
                                .getGenericInterfaces()[0])
                                .getActualTypeArguments()[0]
                );
    }

    private boolean isThisHandlerForThisCommand(Command command, CommandHandlerWithResult<? extends Command, ?> handler) {
        return handler.getClass()
                .getGenericInterfaces()[0] instanceof ParameterizedType &&
                command.getClass().equals(
                        (Class<?>) ((ParameterizedType) handler.getClass()
                                .getGenericInterfaces()[0])
                                .getActualTypeArguments()[0]
                );
    }
}