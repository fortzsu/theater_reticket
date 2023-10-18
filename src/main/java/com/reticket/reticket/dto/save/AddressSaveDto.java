package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressSaveDto {

    private String postCode;
    private String city;
    private String street;
    private Integer houseNumber;
    private Long auditoriumId;

    public AddressSaveDto() {
    }

    public AddressSaveDto(String postCode, String city, String street, Integer houseNumber, Long auditoriumId) {
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.auditoriumId = auditoriumId;
    }

}
