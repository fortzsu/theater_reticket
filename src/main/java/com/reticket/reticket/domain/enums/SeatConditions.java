package com.reticket.reticket.domain.enums;

public enum SeatConditions {

    AVAILABLE("Available"),
    NOT_AVAILABLE("NotAvailable");

    private final String displayName;

    SeatConditions(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
