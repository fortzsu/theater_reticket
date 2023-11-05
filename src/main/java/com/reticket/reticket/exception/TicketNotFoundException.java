package com.reticket.reticket.exception;

public class TicketNotFoundException extends RuntimeException{

    public TicketNotFoundException() {
        super("The given Ticket is not found!");
    }
}
