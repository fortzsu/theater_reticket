package com.reticket.reticket.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlayDto {

    private String playName;
    private String plot;
    private Long auditoriumId;

    public UpdatePlayDto(String playName, String plot, Long auditoriumId) {
        this.playName = playName;
        this.plot = plot;
        this.auditoriumId = auditoriumId;
    }

}
