package com.reticket.reticket.dto.report_search;

public class FilterReportDto {
    private String ticketCondition;
    private String filterByPath;
    private Long searchId;
    private SearchDateDto searchDateDto;
    private boolean isPerformances;
    private boolean exportToSheet;

    public FilterReportDto(String ticketCondition, String filterByPath,
                           Long searchId, SearchDateDto searchDateDto, boolean isPerformances, boolean exportToSheet) {
        this.ticketCondition = ticketCondition;
        this.filterByPath = filterByPath;
        this.searchId = searchId;
        this.searchDateDto = searchDateDto;
        this.isPerformances = isPerformances;
        this.exportToSheet = exportToSheet;
    }

    public String getTicketCondition() {
        return ticketCondition;
    }

    public void setTicketCondition(String ticketCondition) {
        this.ticketCondition = ticketCondition;
    }

    public String getFilterByPath() {
        return filterByPath;
    }

    public void setFilterByPath(String filterByPath) {
        this.filterByPath = filterByPath;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public SearchDateDto getSearchDateDto() {
        return searchDateDto;
    }

    public void setSearchDateDto(SearchDateDto searchDateDto) {
        this.searchDateDto = searchDateDto;
    }

    public boolean isPerformances() {
        return isPerformances;
    }

    public void setPerformances(boolean performances) {
        isPerformances = performances;
    }

    public boolean isExportToSheet() {
        return exportToSheet;
    }

    public void setExportToSheet(boolean exportToSheet) {
        this.exportToSheet = exportToSheet;
    }
}
