package com.reticket.reticket.dto.report_search;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Long getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Long ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Integer getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getTicketCondition() {
        return ticketCondition;
    }

    public void setTicketCondition(String ticketCondition) {
        this.ticketCondition = ticketCondition;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public List<ReportResultPerformancesDto> getCriteriaResultPerformancesDtos() {
        return reportResultPerformancesDtos;
    }

    public void setCriteriaResultPerformancesDtos(List<ReportResultPerformancesDto> reportResultPerformancesDtos) {
        this.reportResultPerformancesDtos = reportResultPerformancesDtos;
    }

    public String getSearchPathName() {
        return searchPathName;
    }

    public void setSearchPathName(String searchPathName) {
        this.searchPathName = searchPathName;
    }
}
