package com.reticket.reticket.domain.enums;

public enum AppUserType {

    GUEST("Guest"),
    THEATRE_USER("TheatreUser"),
    THEATRE_ADMIN("TheatreAdmin"),
    SUPER_ADMIN("SuperAdmin");

    private final String displayName;

    AppUserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
