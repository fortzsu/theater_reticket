package com.reticket.reticket.exception;

public class PerformanceNotFoundException extends RuntimeException {

    public PerformanceNotFoundException() {
        super("The given Performance is not found!");
    }

}
