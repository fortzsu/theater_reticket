package com.reticket.reticket.exception;

public class ContributorNotFoundException extends RuntimeException{

    public ContributorNotFoundException() {
        super("The given Contributor is not found!");
    }
}
