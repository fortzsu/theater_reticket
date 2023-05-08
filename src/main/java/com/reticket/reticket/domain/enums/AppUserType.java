package com.reticket.reticket.domain.enums;

public enum AppUserType {

    GUEST("Guest"),
    SUPER_ADMIN("SuperAdmin"),
    THEATRE_ADMIN("TheatreAdmin"),
    THEATRE_VIEWER("TheatreViewer"),
    THEATRE_USER("TheatreUser");

//    private static final String ROLE_PREFIX = "ROLE_";

    private final String displayName;

    AppUserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
