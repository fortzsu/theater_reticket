package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Performance;

public class PerformanceListDto {

    private String performanceDateTime;

    private String playName;

    private String theatreName;

    private String auditoriumName;

    public PerformanceListDto() {
    }

    public PerformanceListDto(Performance performance) {
        this.performanceDateTime = String.valueOf(performance.getPerformanceDateTime());
        this.playName = performance.getPlay().getPlayName();
        this.theatreName = performance.getPlay().getAuditorium().getTheatre().getTheatreName();
        this.auditoriumName = performance.getPlay().getAuditorium().getAuditoriumName();
    }

    public String getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(String performanceDateTime) {
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
