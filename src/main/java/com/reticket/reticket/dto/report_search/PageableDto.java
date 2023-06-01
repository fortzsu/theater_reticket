package com.reticket.reticket.dto.report_search;

public class PageableDto {

    private Integer page;

    private Integer pageSize;



    public PageableDto(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageableDto() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
