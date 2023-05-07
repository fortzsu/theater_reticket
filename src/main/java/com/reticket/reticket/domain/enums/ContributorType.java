package com.reticket.reticket.domain.enums;

public enum ContributorType {

    ACTOR("Actor"),
    DIRECTOR("NotAvailable"),
    ASSISTANT("Assistant"),
    AUTHOR("Author");

    private final String displayName;

    ContributorType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
