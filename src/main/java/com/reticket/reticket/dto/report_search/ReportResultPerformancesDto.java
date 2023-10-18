package com.reticket.reticket.dto.report_search;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportResultPerformancesDto {

    private LocalDateTime performanceDateTime;
    private Long countOfTicket;
    private Integer sumOfTickets;
    private String theatreName;
    private String auditoriumName;
    private String playName;

    public ReportResultPerformancesDto(LocalDateTime performanceDateTime, Long countOfTicket, Integer sumOfTickets,
                                       String theatreName, String auditoriumName, String playName) {
        this.performanceDateTime = performanceDateTime;
        this.countOfTicket = countOfTicket;
        this.sumOfTickets = sumOfTickets;
        this.theatreName = theatreName;
        this.auditoriumName = auditoriumName;
        this.playName = playName;
    }


}
