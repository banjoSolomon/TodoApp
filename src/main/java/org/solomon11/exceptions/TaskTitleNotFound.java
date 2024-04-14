package org.solomon11.exceptions;

public class TaskTitleNotFound extends RuntimeException{
    public TaskTitleNotFound(String message){
        super(message);
    }
}
