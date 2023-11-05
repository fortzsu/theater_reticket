package com.reticket.reticket.exception;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException() {
        super("The given Address is not found!");
    }
}
