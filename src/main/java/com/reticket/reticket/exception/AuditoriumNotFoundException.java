package com.reticket.reticket.exception;

public class AuditoriumNotFoundException extends RuntimeException{

    public AuditoriumNotFoundException(String message) {
        super(message);
    }
}
