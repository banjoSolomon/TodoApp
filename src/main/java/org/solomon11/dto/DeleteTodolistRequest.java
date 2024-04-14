package org.solomon11.dto;

import lombok.Data;

@Data
public class DeleteTodolistRequest {
    private String author;
    private String listId;
}
