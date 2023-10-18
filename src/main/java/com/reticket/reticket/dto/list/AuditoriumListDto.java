package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuditoriumListDto {

    private String auditoriumName;
    private String auditoriumAddress;

    public AuditoriumListDto(String auditoriumName, String auditoriumAddress) {
        this.auditoriumName = auditoriumName;
        this.auditoriumAddress = auditoriumAddress;
    }

}
