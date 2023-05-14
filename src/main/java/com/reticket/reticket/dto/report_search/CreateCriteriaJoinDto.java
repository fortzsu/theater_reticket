package com.reticket.reticket.dto.report_search;

import com.reticket.reticket.domain.*;
import jakarta.persistence.criteria.Join;

public class CreateCriteriaJoinDto {


    public static void create(CriteriaJoinDto criteriaJoinDto, Join<Performance, Play> playJoin) {
        Join<Play, Auditorium> auditoriumJoin = playJoin.join("auditorium");
        criteriaJoinDto.setAuditoriumJoin(auditoriumJoin);
        Join<Auditorium, Theater> theaterJoin = auditoriumJoin.join("theater");
        criteriaJoinDto.setTheaterJoin(theaterJoin);
    }




}
