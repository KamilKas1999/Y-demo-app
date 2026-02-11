package com.kasprzak.kamil.demoapp.user.service;

import com.kasprzak.kamil.demoapp.user.Role;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {


    Long createUser(final String name, final String lastName, final String email,
                    final String password, final Role role);

    List<UserEntity> getUsers();
    UserEntity getUsersById(long id) throws UserNotFoundException;


    void deleteUser(long id);
}
