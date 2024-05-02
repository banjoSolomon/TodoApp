package org.solomon11.exceptions;

public class TaskExistsException extends RuntimeException {
    public TaskExistsException(String message) {
        super(message);
    }
}
