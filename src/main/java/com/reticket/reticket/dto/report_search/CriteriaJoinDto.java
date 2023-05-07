package com.reticket.reticket.dto.report_search;

import com.reticket.reticket.domain.*;
import jakarta.persistence.criteria.Join;

public class CriteriaJoinDto {

    Join<Ticket, Performance> performanceJoin;
    Join<Performance, Play> playJoin;
    Join<Play, Auditorium> auditoriumJoin;
    Join<Auditorium, Theatre> theatreJoin;
    Join<Ticket, Price> priceJoin;

    public CriteriaJoinDto() {
    }

    public Join<Ticket, Performance> getPerformanceJoin() {
        return performanceJoin;
    }

    public void setPerformanceJoin(Join<Ticket, Performance> performanceJoin) {
        this.performanceJoin = performanceJoin;
    }

    public Join<Performance, Play> getPlayJoin() {
        return playJoin;
    }

    public void setPlayJoin(Join<Performance, Play> playJoin) {
        this.playJoin = playJoin;
    }

    public Join<Play, Auditorium> getAuditoriumJoin() {
        return auditoriumJoin;
    }

    public void setAuditoriumJoin(Join<Play, Auditorium> auditoriumJoin) {
        this.auditoriumJoin = auditoriumJoin;
    }

    public Join<Auditorium, Theatre> getTheatreJoin() {
        return theatreJoin;
    }

    public void setTheatreJoin(Join<Auditorium, Theatre> theatreJoin) {
        this.theatreJoin = theatreJoin;
    }

    public Join<Ticket, Price> getPriceJoin() {
        return priceJoin;
    }

    public void setPriceJoin(Join<Ticket, Price> priceJoin) {
        this.priceJoin = priceJoin;
    }
}
