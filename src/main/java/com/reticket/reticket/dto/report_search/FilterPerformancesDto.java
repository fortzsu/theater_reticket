package com.reticket.reticket.dto.report_search;


public class FilterPerformancesDto {

    private String path;
    private Long searchId;

    private SearchDateDto searchDateDto;
    private PageableDto pageableDto;

    public FilterPerformancesDto() {
    }

    public FilterPerformancesDto(String path, Long searchId, SearchDateDto searchDateDto, PageableDto pageableDto) {
        this.path = path;
        this.searchId = searchId;
        this.searchDateDto = searchDateDto;
        this.pageableDto = pageableDto;
    }

    public SearchDateDto getSearchDateDto() {
        return searchDateDto;
    }

    public void setSearchDateDto(SearchDateDto searchDateDto) {
        this.searchDateDto = searchDateDto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public PageableDto getPageableDto() {
        return pageableDto;
    }

    public void setPageableDto(PageableDto pageableDto) {
        this.pageableDto = pageableDto;
    }
}
