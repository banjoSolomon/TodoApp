package org.solomon11.response;

import lombok.Data;
import org.solomon11.models.TaskStatus;

import java.time.LocalDateTime;
@Data
public class TodoListResponse {
    private String title;
    private TaskStatus status;
    private String listId;
    private String dateCreated;

}
