package com.reticket.reticket.dto.report_search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ReportResultDto {

    private String ticketCondition;
    private Long ticketCount;
    private Integer ticketAmount;
    private String searchPathName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private List<ReportResultPerformancesDto> reportResultPerformancesDtos = new ArrayList<>();

    public ReportResultDto(Long ticketCount, Integer ticketAmount) {
        this.ticketCount = ticketCount;
        this.ticketAmount = ticketAmount;
    }


}
