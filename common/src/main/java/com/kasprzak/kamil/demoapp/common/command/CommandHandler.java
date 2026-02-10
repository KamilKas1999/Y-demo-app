package com.kasprzak.kamil.demoapp.common.command;


import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;

public interface CommandHandler<T> {

    void handle(T command) throws BusinesException;
}
