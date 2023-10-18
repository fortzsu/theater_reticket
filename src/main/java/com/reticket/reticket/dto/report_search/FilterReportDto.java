package com.reticket.reticket.dto.report_search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
