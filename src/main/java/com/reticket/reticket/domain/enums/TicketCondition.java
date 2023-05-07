package com.reticket.reticket.domain.enums;

public enum TicketCondition {
    RESERVED("Reserved"),
    RETURNED("Returned"),
    FOR_SALE("For Sale"),
    SOLD("Sold");

    private String displayName;

    TicketCondition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
