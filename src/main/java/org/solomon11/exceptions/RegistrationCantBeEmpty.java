package org.solomon11.exceptions;

public class RegistrationCantBeEmpty extends RuntimeException{
    public RegistrationCantBeEmpty(String message){
        super(message);
    }
}
