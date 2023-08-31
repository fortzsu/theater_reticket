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

    public static TicketCondition findTicketCondition(String ticketCondition) {
        return switch (ticketCondition) {
            case "SOLD" -> TicketCondition.SOLD;
            case "RETURNED" -> TicketCondition.RETURNED;
            case "RESERVED" -> TicketCondition.RESERVED;
            default -> TicketCondition.FOR_SALE;
        };
    }
}
