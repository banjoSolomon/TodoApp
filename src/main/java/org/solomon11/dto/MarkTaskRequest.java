package org.solomon11.dto;

import lombok.Data;
import org.solomon11.models.TaskStatus;
@Data
public class MarkTaskRequest {
    private String username;
    private String title;

}
