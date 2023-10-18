package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContributorSaveDto {

    private String firstName;
    private String lastName;
    private String introduction;

    public ContributorSaveDto(String firstName, String lastName, String introduction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.introduction = introduction;
    }


}
