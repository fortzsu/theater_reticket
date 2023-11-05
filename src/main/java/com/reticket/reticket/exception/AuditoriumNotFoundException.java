package com.reticket.reticket.exception;

public class AuditoriumNotFoundException extends RuntimeException{

    public AuditoriumNotFoundException() {
        super("The given Auditorium is not found!");
    }
}
