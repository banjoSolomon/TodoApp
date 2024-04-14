package org.solomon11.exceptions;

public class UsernameNotFoundException extends RuntimeException{
    public  UsernameNotFoundException(String message){
        super(message);
    }
}
