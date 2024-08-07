package com.reticket.reticket.service.report;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.service.performance.CriteriaBuilderUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.reticket.reticket.domain.enums.TicketCondition.findTicketCondition;
import static com.reticket.reticket.service.report.ReportConstants.*;

@Service
public class ReportQueryService {

    private LocalDateTime searchStartDate;
    private LocalDateTime searchEndDate;

    public void addValuesToSearchDates(LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        this.searchStartDate = searchStartDate;
        this.searchEndDate = searchEndDate;
    }


    CriteriaJoinDto createJoinDto(Root<Ticket> ticketRoot) {
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Ticket, Price> priceJoin = ticketRoot.join("price");
        criteriaJoinDto.setPriceJoin(priceJoin);

        Join<Ticket, Performance> performanceJoin = ticketRoot.join("performance");
        criteriaJoinDto.setPerformanceJoin(performanceJoin);
        Join<Performance, Play> playJoin = performanceJoin.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);
        CriteriaBuilderUtils.createJoins(criteriaJoinDto, playJoin);
        return criteriaJoinDto;
    }

    List<Predicate> fillPredicateList(FilterReportDto dto, Root<Ticket> root,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              TicketCondition ticketCondition) {

        List<Predicate> predicateList = new ArrayList<>();
        findPathForPredicate(dto, predicateList, criteriaBuilder, criteriaJoinDto);
        findTicketConditionForPredicate(dto, predicateList, criteriaBuilder, root, ticketCondition);

        predicateList.add(criteriaBuilder.greaterThan(criteriaJoinDto.getPerformanceJoin().get(ORIGINAL_DATE_TIME.getDisplayName()), this.searchStartDate));
        predicateList.add(criteriaBuilder.lessThan(criteriaJoinDto.getPerformanceJoin().get(ORIGINAL_DATE_TIME.getDisplayName()), this.searchEndDate));

        return predicateList;
    }



    void findTicketConditionForPredicate(FilterReportDto dto, List<Predicate> predicateList,
                                                 CriteriaBuilder criteriaBuilder, Root<Ticket> root, TicketCondition ticketCondition) {
        if (dto.getTicketCondition().equals("SOLD")) {
            Predicate sold = criteriaBuilder.equal(root.get(TICKET_CONDITION.getDisplayName()), findTicketCondition(dto.getTicketCondition()));
            Predicate returned = criteriaBuilder.equal(root.get(TICKET_CONDITION.getDisplayName()), TicketCondition.RETURNED);
            Predicate soldReturned = criteriaBuilder.or(sold, returned);
            predicateList.add(soldReturned);
        } else {
            predicateList.add(criteriaBuilder.equal(root.get(TICKET_CONDITION.getDisplayName()),
                    ticketCondition));
        }
    }

    void findPathForPredicate(FilterReportDto dto, List<Predicate> predicateList,
                                      CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto) {
        if (dto.getFilterByPath().equals(THEATER.getDisplayName())) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getTheaterJoin().get(ID.getDisplayName()), dto.getSearchId()));
        } else if (dto.getFilterByPath().equals(AUDITORIUM.getDisplayName())) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getAuditoriumJoin().get(ID.getDisplayName()), dto.getSearchId()));
        } else {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get(ID.getDisplayName()), dto.getSearchId()));
        }
    }





}
