package com.reticket.reticket.security;

public enum RoleEnum {

    GUEST,
    THEATRE_USER,
    THEATRE_ADMIN,
    SUPER_ADMIN;

        private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public String toString() {
        return ROLE_PREFIX + this.name();
    }
}
