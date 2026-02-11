package com.kasprzak.kamil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.demoapp.IntegrationTestConfig;
import com.kasprzak.kamil.demoapp.notification.CreateNotificationRequest;
import com.kasprzak.kamil.demoapp.notification.service.NotificationService;
import com.kasprzak.kamil.demoapp.user.Role;
import com.kasprzak.kamil.demoapp.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = IntegrationTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
})
public class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Test
    void shouldReturnList() throws Exception {
        var userId = userService.createUser("test", "test", "Test", "test", Role.USER);
        notificationService.createNotification(userId, "Test Topic", "Test Content");

        mockMvc.perform(get("/notification/{userId}", userId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].topic").value("Test Topic"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    void shouldReturnCreated() throws Exception {
        var userId = userService.createUser("test", "test", "Test", "test", Role.USER);
        var request = new CreateNotificationRequest("New Topic", "New Content");

        mockMvc.perform(post("/notification/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void shouldReturnOk() throws Exception {
        var userId = userService.createUser("test", "test", "Test", "test", Role.USER);
        var notificationId = notificationService.createNotification(userId, "Update Topic", "Update Content");

        mockMvc.perform(put("/notification/{notificationId}", notificationId))
                .andExpect(status().isOk());
    }
}
