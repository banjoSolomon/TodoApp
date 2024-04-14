package org.solomon11.dto;

import lombok.Data;
import org.solomon11.models.TaskStatus;

@Data
public class TodolistRequest {
    private String username;
    private String title;
    private TaskStatus status;

}
