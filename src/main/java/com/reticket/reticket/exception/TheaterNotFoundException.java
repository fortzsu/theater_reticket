package com.reticket.reticket.exception;

public class TheaterNotFoundException extends RuntimeException{

    public TheaterNotFoundException() {
        super("The given Theater is not found!");
    }
}
