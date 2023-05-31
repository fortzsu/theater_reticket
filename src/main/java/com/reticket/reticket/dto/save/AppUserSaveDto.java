package com.reticket.reticket.dto.save;

public class AppUserSaveDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String appUserType;
    private String email;

    public AppUserSaveDto(String firstName, String lastName, String username, String password, String appUserType, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.appUserType = appUserType;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(String appUserType) {
        this.appUserType = appUserType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
