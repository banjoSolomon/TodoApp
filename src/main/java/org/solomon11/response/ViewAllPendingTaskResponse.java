package org.solomon11.response;

import lombok.Data;
import org.solomon11.models.TaskStatus;

@Data
public class ViewAllPendingTaskResponse {
    private String title;
    private String listId;
    private String dateCreated;
    private TaskStatus status;
}
