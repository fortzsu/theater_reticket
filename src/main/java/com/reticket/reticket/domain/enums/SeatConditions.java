package com.reticket.reticket.domain.enums;

import lombok.Getter;

@Getter
public enum SeatConditions {

    AVAILABLE("Available"),
    NOT_AVAILABLE("NotAvailable");

    private final String displayName;

    SeatConditions(String displayName) {
        this.displayName = displayName;
    }

}
