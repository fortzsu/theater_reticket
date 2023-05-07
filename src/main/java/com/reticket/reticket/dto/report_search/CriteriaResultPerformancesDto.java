package com.reticket.reticket.dto.report_search;

import java.time.LocalDateTime;

public class CriteriaResultPerformancesDto {

    private LocalDateTime performanceDateTime;
    private Long countOfTicket;
    private Integer sumOfTickets;
    private String theatreName;
    private String auditoriumName;
    private String playName;

    public CriteriaResultPerformancesDto(LocalDateTime performanceDateTime, Long countOfTicket, Integer sumOfTickets,
                                         String theatreName, String auditoriumName, String playName) {
        this.performanceDateTime = performanceDateTime;
        this.countOfTicket = countOfTicket;
        this.sumOfTickets = sumOfTickets;
        this.theatreName = theatreName;
        this.auditoriumName = auditoriumName;
        this.playName = playName;
    }

    public LocalDateTime getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(LocalDateTime performanceDateTime) {
        this.performanceDateTime = performanceDateTime;
    }

    public Long getCountOfTicket() {
        return countOfTicket;
    }

    public void setCountOfTicket(Long countOfTicket) {
        this.countOfTicket = countOfTicket;
    }

    public Object getSumOfTickets() {
        return sumOfTickets;
    }

    public void setSumOfTickets(Integer sumOfTickets) {
        this.sumOfTickets = sumOfTickets;
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
