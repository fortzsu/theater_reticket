package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.*;
import com.reticket.reticket.google.GoogleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;
    @PersistenceContext
    private EntityManager entityManagerPerformances;
    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;
    private final PlayService playService;

    private final GoogleService googleService;

    public ReportService(TheaterService theaterService, AuditoriumService auditoriumService, PlayService playService, GoogleService googleService) {
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
        this.playService = playService;
        this.googleService = googleService;
    }

    public CriteriaResultDto report(ReportFilterDto reportFilterDto) {
        TicketCondition ticketCondition = findTicketCondition(reportFilterDto.getTicketCondition());
        LocalDateTime startDate = LocalDateTime.of(reportFilterDto.getSearchDateDto().getStartYear(), reportFilterDto.getSearchDateDto().getStartMonth(), reportFilterDto.getSearchDateDto().getStartDay(), 0, 0);
        LocalDateTime endDate = LocalDateTime.of(reportFilterDto.getSearchDateDto().getEndYear(), reportFilterDto.getSearchDateDto().getEndMonth(), reportFilterDto.getSearchDateDto().getEndDay(), 23, 59);
        CriteriaResultDto criteriaResultDto = fillResultReport(reportFilterDto, ticketCondition, startDate, endDate);
        criteriaResultDto.setTicketCondition(ticketCondition.getDisplayName());
        criteriaResultDto.setStart(startDate);
        criteriaResultDto.setEnd(endDate);
        if (reportFilterDto.isPerformances()) {
            criteriaResultDto.setCriteriaResultPerformancesDtos(fillPerformances(reportFilterDto, ticketCondition, startDate, endDate));
        } else {
            criteriaResultDto.setSearchPathName(searchThePath(reportFilterDto));
        }
//        if(reportFilterDto.isExportToSheet()) {
//            try {
//                this.googleService.exportDataToSheet(criteriaResultDto);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return criteriaResultDto;
    }


    private CriteriaResultDto fillResultReport(ReportFilterDto reportFilterDto, TicketCondition ticketCondition, LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CriteriaResultDto> criteriaQuery = criteriaBuilder.createQuery(CriteriaResultDto.class);
        Root<Ticket> root = criteriaQuery.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDto = fillCriteriaJoinDto(root);

        return entityManager.createQuery(
                criteriaQuery.multiselect(
                                criteriaBuilder.count(root),
                                criteriaBuilder.sum(criteriaJoinDto.getPriceJoin().get("amount"))).
                        where(fillPredicateList(reportFilterDto, root, criteriaBuilder, criteriaJoinDto, ticketCondition, startDate, endDate)
                                .toArray(new Predicate[0]))).getSingleResult();

    }

    private List<CriteriaResultPerformancesDto> fillPerformances(ReportFilterDto reportFilterDto,
                                                                 TicketCondition ticketCondition, LocalDateTime queryStart, LocalDateTime queryEnd) {
        CriteriaBuilder criteriaBuilderPerformances = entityManager.getCriteriaBuilder();
        CriteriaQuery<CriteriaResultPerformancesDto> criteriaQueryPerformances = criteriaBuilderPerformances.createQuery(CriteriaResultPerformancesDto.class);
        Root<Ticket> rootPerformances = criteriaQueryPerformances.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDtoPerformances = fillCriteriaJoinDto(rootPerformances);
        return entityManagerPerformances.createQuery(
                        criteriaQueryPerformances.multiselect(
                                        criteriaJoinDtoPerformances.getPerformanceJoin().get("performanceDateTime"),
                                        criteriaBuilderPerformances.count(rootPerformances),
                                        criteriaBuilderPerformances.sum(criteriaJoinDtoPerformances.getPriceJoin().get("amount")),
                                        criteriaJoinDtoPerformances.getTheaterJoin().get("theaterName"),
                                        criteriaJoinDtoPerformances.getAuditoriumJoin().get("auditoriumName"),
                                        criteriaJoinDtoPerformances.getPlayJoin().get("playName")).
                                groupBy(rootPerformances.get("performance")).where(fillPredicateList(reportFilterDto, rootPerformances,
                                        criteriaBuilderPerformances, criteriaJoinDtoPerformances, ticketCondition, queryStart, queryEnd).toArray(new Predicate[0]))).
                getResultList();
    }

    private CriteriaJoinDto fillCriteriaJoinDto(Root<Ticket> root) {
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Ticket, Performance> performanceJoin = root.join("performance");
        criteriaJoinDto.setPerformanceJoin(performanceJoin);
        Join<Performance, Play> playJoin = performanceJoin.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);
        Join<Play, Auditorium> auditoriumJoin = playJoin.join("auditorium");
        criteriaJoinDto.setAuditoriumJoin(auditoriumJoin);
        Join<Auditorium, Theater> theaterJoin = auditoriumJoin.join("theater");
        criteriaJoinDto.setTheaterJoin(theaterJoin);
        Join<Ticket, Price> priceJoin = root.join("price");
        criteriaJoinDto.setPriceJoin(priceJoin);
        return criteriaJoinDto;
    }

    private List<Predicate> fillPredicateList(ReportFilterDto reportFilterDto, Root<Ticket> root,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              TicketCondition ticketCondition, LocalDateTime queryStart, LocalDateTime queryEnd) {

        List<Predicate> predicateList = new ArrayList<>();

        if (reportFilterDto.getFilterByPath().equals("theater")) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getTheaterJoin().get("id"), reportFilterDto.getSearchId()));
        } else if (reportFilterDto.getFilterByPath().equals("auditorium")) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getAuditoriumJoin().get("id"), reportFilterDto.getSearchId()));
        } else {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get("id"), reportFilterDto.getSearchId()));
        }
        if (reportFilterDto.getTicketCondition().equals("SOLD")) {
            Predicate sold = criteriaBuilder.equal(root.get("ticketCondition"), findTicketCondition(reportFilterDto.getTicketCondition()));
            Predicate returned = criteriaBuilder.equal(root.get("ticketCondition"), TicketCondition.RETURNED);
            Predicate soldReturned = criteriaBuilder.or(sold, returned);
            predicateList.add(soldReturned);
        } else {
            predicateList.add(criteriaBuilder.equal(root.get("ticketCondition"),
                    ticketCondition));
        }
        predicateList.add(criteriaBuilder.greaterThan(criteriaJoinDto.getPerformanceJoin().get("performanceDateTime"), queryStart));
        predicateList.add(criteriaBuilder.lessThan(criteriaJoinDto.getPerformanceJoin().get("performanceDateTime"), queryEnd));

        return predicateList;
    }

    private TicketCondition findTicketCondition(String ticketCondition) {
        return switch (ticketCondition) {
            case "SOLD" -> TicketCondition.SOLD;
            case "RETURNED" -> TicketCondition.RETURNED;
            case "RESERVED" -> TicketCondition.RESERVED;
            default -> TicketCondition.FOR_SALE;
        };
    }

    private String searchThePath(ReportFilterDto reportFilterDto) {
        switch (reportFilterDto.getFilterByPath()) {
            case "theater" -> {
                Theater theater = this.theaterService.findById(reportFilterDto.getSearchId());
                return theater.getTheaterName();
            }
            case "auditorium" -> {
                Auditorium auditorium = this.auditoriumService.findAuditoriumById(reportFilterDto.getSearchId());
                return auditorium.getAuditoriumName();
            }
            default -> {
                Play play = this.playService.findById(reportFilterDto.getSearchId());
                return play.getPlayName();
            }
        }
    }


}
