package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Performance;

import java.time.LocalDateTime;

import com.reticket.reticket.dto.wrapper.WrapperClass;
import com.reticket.reticket.dto.wrapper.WrapperClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
