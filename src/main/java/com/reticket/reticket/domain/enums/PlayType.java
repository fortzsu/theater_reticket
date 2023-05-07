package com.reticket.reticket.domain.enums;

public enum PlayType {


    DRAMA("Drama"),
    TRAGEDY("Tragedy"),
    POLITICAL("Political"),
    CRIME("Crime"),
    COMEDY("Comedy"),
    MUSICAL("Musical");

    private final String displayName;

    PlayType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
