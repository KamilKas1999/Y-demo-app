package com.kasprzak.kamil.demoapp.auth.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class NewUserRegisteredEvent implements Serializable {
    private Long userId;
}
