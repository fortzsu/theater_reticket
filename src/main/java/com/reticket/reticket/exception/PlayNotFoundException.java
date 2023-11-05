package com.reticket.reticket.exception;

public class PlayNotFoundException extends RuntimeException{

    public PlayNotFoundException() {
        super("The given Play is not found!");
    }
}
