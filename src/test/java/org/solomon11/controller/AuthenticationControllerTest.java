package org.solomon11.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.solomon11.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authenticateUserTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("password");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testThatAuthenticationFailsForIncorrectCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("password");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isUnauthorized()).andDo(print());


    }


}
