package com.reticket.reticket.dto.report_search;

import com.reticket.reticket.domain.*;
import jakarta.persistence.criteria.Join;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CriteriaJoinDto {

    Join<Ticket, Performance> performanceJoin;
    Join<Performance, Play> playJoin;
    Join<Play, Auditorium> auditoriumJoin;
    Join<Auditorium, Theater> theaterJoin;
    Join<Ticket, Price> priceJoin;

}
