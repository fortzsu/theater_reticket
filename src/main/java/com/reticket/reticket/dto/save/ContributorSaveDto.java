package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContributorSaveDto {

    private String firstName;
    private String lastName;
    private String introduction;

    public ContributorSaveDto() {
    }

    public ContributorSaveDto(String firstName, String lastName, String introduction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.introduction = introduction;
    }


}
