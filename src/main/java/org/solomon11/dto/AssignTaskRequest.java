package org.solomon11.dto;

import lombok.Data;

@Data
public class AssignTaskRequest {
    private String username;
    private String title;
    private String assignee;
    private String author;


}
