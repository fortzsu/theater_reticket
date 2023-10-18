package com.reticket.reticket.dto.save;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
public class PerformanceSaveDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime performanceDateTime;
    private Long playId;

    public PerformanceSaveDto(LocalDateTime performanceDateTime, Long playId) {
        this.performanceDateTime = performanceDateTime;
        this.playId = playId;
    }


}
