package com.kasprzak.kamil.demoapp.user.service;

import com.kasprzak.kamil.demoapp.user.Role;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getUsersById(long id) throws UserNotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Long createUser(final String name, final String lastName, final String email,
                           final String password, final Role role) {
        var user = UserEntity.builder()
                .firstname(name)
                .lastname(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return userRepository.save(user).getId();
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }



    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
