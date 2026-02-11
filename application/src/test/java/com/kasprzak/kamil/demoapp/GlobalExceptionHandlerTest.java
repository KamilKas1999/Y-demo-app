package com.kasprzak.kamil.demoapp;

import com.kasprzak.kamil.demoapp.exception.ErrorResponse;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleRuntimeException_returns500() {
        // given
        RuntimeException ex = new RuntimeException("Runtime exception");

        // when
        ResponseEntity<ErrorResponse> response = handler.handle(ex);

        // then
        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Runtime exception", response.getBody().message());
    }

    @Test
    void handleGenericException_returns500() {
        //given
        Exception ex = new Exception("Generic exception");

        // when
        ResponseEntity<ErrorResponse> response = handler.handle(ex);
        
        // then
        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Generic exception", response.getBody().message());
    }

    @Test
    void handleUserNotFoundException_returns404() {
        //given
        UserNotFoundException ex = new UserNotFoundException();

        //when
        ResponseEntity<ErrorResponse> response = handler.handle(ex);

        // when
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}