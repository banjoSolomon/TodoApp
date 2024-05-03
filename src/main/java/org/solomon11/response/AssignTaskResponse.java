package org.solomon11.response;

import lombok.Data;
import org.solomon11.models.TaskStatus;

@Data
public class AssignTaskResponse {
    private String title;
    private String listId;
    private TaskStatus status;
    private String assignee;
    private String dateUpdated;

}