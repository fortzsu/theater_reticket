package com.reticket.reticket.security;

public enum RoleEnum {

    GUEST,
    THEATRE_ADMIN,
    THEATRE_USER,
    THEATRE_VIEWER,
    SUPER_ADMIN;

        private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public String toString() {
        return ROLE_PREFIX + this.name();
    }
}
