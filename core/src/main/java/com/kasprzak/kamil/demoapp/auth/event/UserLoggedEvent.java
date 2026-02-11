package com.kasprzak.kamil.demoapp.auth.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserLoggedEvent implements Serializable {
    private Long userId;

}
