package com.reticket.reticket.dto.report_search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterPerformancesDto {

    private String path;
    private Long searchId;
    private SearchDateDto searchDateDto;
    private PageableDto pageableDto;

    public FilterPerformancesDto(String path, Long searchId, SearchDateDto searchDateDto, PageableDto pageableDto) {
        this.path = path;
        this.searchId = searchId;
        this.searchDateDto = searchDateDto;
        this.pageableDto = pageableDto;
    }


}
