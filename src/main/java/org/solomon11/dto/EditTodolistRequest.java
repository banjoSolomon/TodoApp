package org.solomon11.dto;

import lombok.Data;
import org.solomon11.models.TaskStatus;

@Data
public class EditTodolistRequest {
    private String author;
    private String title;
    private TaskStatus status;
    private String listId;
}
