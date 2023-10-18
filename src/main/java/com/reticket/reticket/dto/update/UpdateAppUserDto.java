package com.reticket.reticket.dto.update;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAppUserDto {

    private String email;
    private String password;

    public UpdateAppUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
