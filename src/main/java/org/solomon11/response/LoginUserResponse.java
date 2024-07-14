package org.solomon11.response;

import lombok.Data;

@Data
public class LoginUserResponse {
    private String username;
    private String id;
    private String message;
    private String token;
}
