package org.solomon11.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class TodoList {
    @Id
    private String id;
    private String title;
    private TaskStatus status;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateUpdated = LocalDateTime.now();
    private LocalDateTime startTime;
    private LocalDateTime endTime;





}
