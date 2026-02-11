package com.kasprzak.kamil.demoapp.auth.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
public class NewUserRegisteredEvent implements Serializable {
    private Long userId;

    @JsonCreator
    public NewUserRegisteredEvent(@JsonProperty("userId") Long userId) {
        this.userId = userId;
    }
}
