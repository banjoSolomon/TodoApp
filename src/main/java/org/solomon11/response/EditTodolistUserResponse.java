package org.solomon11.response;

import lombok.Data;
import org.solomon11.models.TaskStatus;

@Data
public class EditTodolistUserResponse {
    private String title;
    private TaskStatus status;
    private String listId;
    private String dateUpdated;
}
