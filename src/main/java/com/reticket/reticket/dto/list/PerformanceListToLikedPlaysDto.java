package com.reticket.reticket.dto.list;

import java.time.LocalDateTime;

public class PerformanceListToLikedPlaysDto {

    private LocalDateTime performanceDateTime;

    public LocalDateTime getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(LocalDateTime performanceDateTime) {
        this.performanceDateTime = performanceDateTime;
    }
}
