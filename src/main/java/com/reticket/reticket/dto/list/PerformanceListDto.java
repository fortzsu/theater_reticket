package com.reticket.reticket.dto.list;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceListDto {

    private LocalDateTime performanceDateTime;

    private String playName;

    private String theatreName;

    private String auditoriumName;

    public PerformanceListDto(LocalDateTime performanceDateTime, String playName, String theatreName, String auditoriumName) {
        this.performanceDateTime = performanceDateTime;
        this.playName = playName;
        this.theatreName = theatreName;
        this.auditoriumName = auditoriumName;
    }

}
