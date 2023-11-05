package com.reticket.reticket.exception;

public class AppUserNotFoundException extends RuntimeException{

    public AppUserNotFoundException() {
        super("The given AppUser is not found!");
    }
}
