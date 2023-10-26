package com.reticket.reticket.service.performance;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.service.AuditoriumService;
import com.reticket.reticket.service.PlayService;
import com.reticket.reticket.service.TheaterService;
import jakarta.persistence.criteria.Join;

public class CriteriaBuilderUtils {

    public static void createJoins(CriteriaJoinDto criteriaJoinDto, Join<Performance, Play> playJoin) {
        Join<Play, Auditorium> auditoriumJoin = playJoin.join("auditorium");
        criteriaJoinDto.setAuditoriumJoin(auditoriumJoin);
        Join<Auditorium, Theater> theaterJoin = auditoriumJoin.join("theater");
        criteriaJoinDto.setTheaterJoin(theaterJoin);
    }








}
