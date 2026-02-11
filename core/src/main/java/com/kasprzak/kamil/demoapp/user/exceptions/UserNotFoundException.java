package com.kasprzak.kamil.demoapp.user.exceptions;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;

public class UserNotFoundException extends BusinesException {


    public UserNotFoundException(){
        super("User not found");
    }

    public UserNotFoundException(long userId){
        super("User with " + userId + " not found");
    }

}
