package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Performance;

import java.time.LocalDateTime;

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

    public PerformanceListDto() {
    }

    public PerformanceListDto(Performance performance) {
        this.performanceDateTime = performance.getOriginalPerformanceDateTime();
        this.playName = performance.getPlay().getPlayName();
        this.theatreName = performance.getPlay().getAuditorium().getTheater().getTheaterName();
        this.auditoriumName = performance.getPlay().getAuditorium().getAuditoriumName();
    }

    public LocalDateTime getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(LocalDateTime performanceDateTime) {
        this.performanceDateTime = performanceDateTime;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }
}
