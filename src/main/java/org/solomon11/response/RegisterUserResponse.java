package org.solomon11.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RegisterUserResponse {
    private String username;
    private String id;
    private String dateRegistered;
    private String message;


}