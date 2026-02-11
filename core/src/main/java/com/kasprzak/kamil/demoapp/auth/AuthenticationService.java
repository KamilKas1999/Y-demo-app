package com.kasprzak.kamil.demoapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.demoapp.auth.event.NewUserRegisteredEvent;
import com.kasprzak.kamil.demoapp.common.command.CommandExecutor;
import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import com.kasprzak.kamil.demoapp.common.kafka.producer.KafkaProducer;
import com.kasprzak.kamil.demoapp.common.query.QueryExecutor;
import com.kasprzak.kamil.demoapp.token.Token;
import com.kasprzak.kamil.demoapp.token.TokenRepository;
import com.kasprzak.kamil.demoapp.token.TokenType;
import com.kasprzak.kamil.demoapp.user.UserEntity;
import com.kasprzak.kamil.demoapp.user.UserRepository;
import com.kasprzak.kamil.demoapp.user.command.user.create.CreateUserCommand;
import com.kasprzak.kamil.demoapp.user.command.user.create.CreateUserCommandResult;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import com.kasprzak.kamil.demoapp.user.query.one.UserQuery;
import com.kasprzak.kamil.demoapp.user.query.one.UsersQueryResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final CommandExecutor commandExecutor;
    private final QueryExecutor queryExecutor;

    private final KafkaProducer kafkaProducer;

    public AuthenticationResponse register(RegisterRequest request) throws BusinesException {

        var commandResult = createUser(request);

        var user = getUser(commandResult);

        var jwtToken = jwtService.generateToken(user.getUserEntities());
        var refreshToken = jwtService.generateRefreshToken(user.getUserEntities());

        saveUserToken(user.getUserEntities(), jwtToken);

        kafkaProducer.sendEvent(new NewUserRegisteredEvent(user.getUserEntities().getId()));

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private CreateUserCommandResult createUser(RegisterRequest request) throws BusinesException {
        var command = new CreateUserCommand(request.getFirstname(), request.getLastname(), request.getEmail(),
                request.getPassword(), request.getRole());
        return commandExecutor.execute(command, CreateUserCommandResult.class);
    }

    private UsersQueryResult getUser(CreateUserCommandResult commandResult) throws BusinesException {
        var query = new UserQuery(commandResult.userId());
        var user = queryExecutor.execute(query, UsersQueryResult.class);
        return user;
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var userOptional = repository.findByEmail(request.email());
        if (userOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        var user = userOptional.get();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserEntity userEntity, String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity userEntity) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
