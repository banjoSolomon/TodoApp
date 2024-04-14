package org.solomon11.response;

import lombok.Data;

@Data
public class LogoutUserResponse {
    private String username;
    private String id;
    private String message;
}
