package org.solomon11.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Document
public class User {
    private String firstName;
    private String username;
    private List<TodoList> todoList = new ArrayList<>();
    private String password;
    @Id
    private String id;
    private LocalDateTime dateRegistered = LocalDateTime.now();


}
