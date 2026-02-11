package com.kasprzak.kamil.demoapp.user.service;

import com.kasprzak.kamil.demoapp.user.Role;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    void shouldReturnUser() throws UserNotFoundException {
        // given
        long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstname("Jan")
                .lastname("Kowalski")
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // when
        UserEntity result = userService.getUsersById(userId);

        // then
        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        long userId = 1L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // when / then
        UserNotFoundException userNotFoundException =
                assertThrows(UserNotFoundException.class, () -> userService.getUsersById(userId));

        assertTrue(userNotFoundException.getMessage().contains(String.valueOf(userId)));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldCreateUserAndReturnId() {
        // given
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword))
                .thenReturn(encodedPassword);

        UserEntity savedUser = UserEntity.builder()
                .id(10L)
                .build();

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        // when
        Long result = userService.createUser(
                "Jan",
                "Kowalski",
                "jan@test.pl",
                rawPassword,
                Role.USER
        );

        // then
        assertEquals(10L,result);

        ArgumentCaptor<UserEntity> captor =
                ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository).save(captor.capture());
        verify(passwordEncoder).encode(rawPassword);

        UserEntity userToSave = captor.getValue();
        assertEquals(encodedPassword,userToSave.getPassword());
        assertEquals("jan@test.pl",userToSave.getEmail());
        assertEquals(Role.USER,userToSave.getRole());
    }

    @Test
    void shouldReturnAllUsers() {
        // given
        List<UserEntity> users = List.of(
                UserEntity.builder().id(1L).build(),
                UserEntity.builder().id(2L).build()
        );

        when(userRepository.findAll())
                .thenReturn(users);

        // when
        List<UserEntity> result = userService.getUsers();

        // then
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void shouldDeleteUserById() {
        // given
        long userId = 5L;

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).deleteById(userId);
    }

}