package com.reticket.reticket.dto.save;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PerformanceSaveDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime performanceDateTime;
    private Long playId;

    public PerformanceSaveDto() {
    }

    public PerformanceSaveDto(LocalDateTime performanceDateTime, Long playId) {
        this.performanceDateTime = performanceDateTime;
        this.playId = playId;
    }

    public Long getPlayId() {
        return playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }

    public LocalDateTime getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(LocalDateTime performanceDateTime) {
        this.performanceDateTime = performanceDateTime;
    }

}
