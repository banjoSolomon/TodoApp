package org.solomon11.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException{
    public InvalidUsernameOrPassword(String message){
        super(message);
    }
}
