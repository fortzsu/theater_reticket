package com.reticket.reticket.dto.report_search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageableDto {

    private Integer page;

    private Integer pageSize;

    public PageableDto(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

}
