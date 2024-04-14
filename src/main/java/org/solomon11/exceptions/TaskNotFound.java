package org.solomon11.exceptions;

public class TaskNotFound extends RuntimeException{
    public TaskNotFound(String message){
        super(message);
    }
}
