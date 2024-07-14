package org.solomon11.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document
public class User {
    private String firstName;
    private String username;
    private List<String> roles = new ArrayList<>();
    private List<TodoList> todoList = new ArrayList<>();
    private String password;
    @Id
    private String id;
    private LocalDateTime dateRegistered = LocalDateTime.now();

    public List<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());


    }
}