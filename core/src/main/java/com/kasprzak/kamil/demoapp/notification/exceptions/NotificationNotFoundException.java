package com.kasprzak.kamil.demoapp.notification.exceptions;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;

public class NotificationNotFoundException extends BusinesException {

    public NotificationNotFoundException(){
        super("Notification not found");
    }

}
