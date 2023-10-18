package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GuestUserSaveDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    public GuestUserSaveDto() {
    }

    public GuestUserSaveDto(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
