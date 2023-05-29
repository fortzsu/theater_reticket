package com.reticket.reticket.security;

public enum RoleEnum {

    GUEST,
    THEATRE_USER,
    THEATER_ADMIN,
    SUPER;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public String toString() {
        return ROLE_PREFIX + this.name();
    }
}
