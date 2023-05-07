package com.reticket.reticket.dto.report_search;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CriteriaResultDto {
    private String ticketCondition;
    private Long ticketCount;
    private Integer ticketAmount;
    private String searchPathName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private List<CriteriaResultPerformancesDto> criteriaResultPerformancesDtos = new ArrayList<>();

    public CriteriaResultDto(Long ticketCount, Integer ticketAmount) {
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

    public List<CriteriaResultPerformancesDto> getCriteriaResultPerformancesDtos() {
        return criteriaResultPerformancesDtos;
    }

    public void setCriteriaResultPerformancesDtos(List<CriteriaResultPerformancesDto> criteriaResultPerformancesDtos) {
        this.criteriaResultPerformancesDtos = criteriaResultPerformancesDtos;
    }

    public String getSearchPathName() {
        return searchPathName;
    }

    public void setSearchPathName(String searchPathName) {
        this.searchPathName = searchPathName;
    }
}
