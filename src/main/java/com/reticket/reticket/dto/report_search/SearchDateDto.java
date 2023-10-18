package com.reticket.reticket.dto.report_search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDateDto {

    private Integer startYear;
    private Integer startMonth;
    private Integer startDay;
    private Integer endYear;
    private Integer endMonth;
    private Integer endDay;

    public SearchDateDto(Integer startYear, Integer startMonth, Integer startDay,
                         Integer endYear, Integer endMonth, Integer endDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
    }

}
