package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.*;
import com.reticket.reticket.google.GoogleService;
import com.reticket.reticket.utils.CriteriaBuilderUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;
    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;
    private final PlayService playService;
    private final GoogleService googleService;

    public ReportService(TheaterService theaterService, AuditoriumService auditoriumService,
                         PlayService playService, GoogleService googleService) {
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
        this.playService = playService;
        this.googleService = googleService;
    }

    public ReportResultDto report(FilterReportDto filterReportDto) {
        TicketCondition ticketCondition = findTicketCondition(filterReportDto.getTicketCondition());
        LocalDateTime startDate = LocalDateTime.of(filterReportDto.getSearchDateDto().getStartYear(), filterReportDto.getSearchDateDto().getStartMonth(), filterReportDto.getSearchDateDto().getStartDay(), 0, 0);
        LocalDateTime endDate = LocalDateTime.of(filterReportDto.getSearchDateDto().getEndYear(), filterReportDto.getSearchDateDto().getEndMonth(), filterReportDto.getSearchDateDto().getEndDay(), 23, 59);
        ReportResultDto reportResultDto = fillResultReport(filterReportDto, ticketCondition, startDate, endDate);
        reportResultDto.setTicketCondition(ticketCondition.getDisplayName());
        reportResultDto.setStart(startDate);
        reportResultDto.setEnd(endDate);
        if (filterReportDto.isPerformances()) {
            reportResultDto.setCriteriaResultPerformancesDtos(fillPerformances(filterReportDto, ticketCondition, startDate, endDate));
        } else {
            reportResultDto.setSearchPathName(searchThePath(filterReportDto));
        }
        if(filterReportDto.isExportToSheet()) {
            try {
                this.googleService.exportDataToSheet(reportResultDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return reportResultDto;
    }

    private ReportResultDto fillResultReport(FilterReportDto filterReportDto, TicketCondition ticketCondition,
                                             LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReportResultDto> criteriaQuery = criteriaBuilder.createQuery(ReportResultDto.class);
        Root<Ticket> ticketRoot = criteriaQuery.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDto = createJoinDto(ticketRoot);

        return entityManager.createQuery(
                criteriaQuery.multiselect(
                                criteriaBuilder.count(ticketRoot),
                                criteriaBuilder.sum(criteriaJoinDto.getPriceJoin().get("amount"))).
                        where(fillPredicateList(filterReportDto, ticketRoot, criteriaBuilder, criteriaJoinDto,
                                ticketCondition, startDate, endDate)
                                .toArray(new Predicate[0]))).getSingleResult();

    }

    private List<ReportResultPerformancesDto> fillPerformances(FilterReportDto filterReportDto, TicketCondition ticketCondition,
                                                               LocalDateTime queryStart, LocalDateTime queryEnd) {
        CriteriaBuilder criteriaBuilderPerformances = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReportResultPerformancesDto> criteriaQueryPerformances = criteriaBuilderPerformances.createQuery(ReportResultPerformancesDto.class);
        Root<Ticket> ticketRoot = criteriaQueryPerformances.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDtoPerformances = createJoinDto(ticketRoot);
        return entityManager.createQuery(
                        criteriaQueryPerformances.multiselect(
                                        criteriaJoinDtoPerformances.getPerformanceJoin().get("originalPerformanceDateTime"),
                                        criteriaBuilderPerformances.count(ticketRoot),
                                        criteriaBuilderPerformances.sum(criteriaJoinDtoPerformances.getPriceJoin().get("amount")),
                                        criteriaJoinDtoPerformances.getTheaterJoin().get("theaterName"),
                                        criteriaJoinDtoPerformances.getAuditoriumJoin().get("auditoriumName"),
                                        criteriaJoinDtoPerformances.getPlayJoin().get("playName")).
                                groupBy(ticketRoot.get("performance")).where(fillPredicateList(filterReportDto, ticketRoot,
                                        criteriaBuilderPerformances, criteriaJoinDtoPerformances, ticketCondition, queryStart, queryEnd).toArray(new Predicate[0]))).
                getResultList();
    }

    private CriteriaJoinDto createJoinDto(Root<Ticket> ticketRoot) {
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

    private List<Predicate> fillPredicateList(FilterReportDto filterReportDto, Root<Ticket> root,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              TicketCondition ticketCondition, LocalDateTime queryStart, LocalDateTime queryEnd) {

        List<Predicate> predicateList = new ArrayList<>();

        if (filterReportDto.getFilterByPath().equals("theater")) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getTheaterJoin().get("id"), filterReportDto.getSearchId()));
        } else if (filterReportDto.getFilterByPath().equals("auditorium")) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getAuditoriumJoin().get("id"), filterReportDto.getSearchId()));
        } else {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get("id"), filterReportDto.getSearchId()));
        }
        if (filterReportDto.getTicketCondition().equals("SOLD")) {
            Predicate sold = criteriaBuilder.equal(root.get("ticketCondition"), findTicketCondition(filterReportDto.getTicketCondition()));
            Predicate returned = criteriaBuilder.equal(root.get("ticketCondition"), TicketCondition.RETURNED);
            Predicate soldReturned = criteriaBuilder.or(sold, returned);
            predicateList.add(soldReturned);
        } else {
            predicateList.add(criteriaBuilder.equal(root.get("ticketCondition"),
                    ticketCondition));
        }
        predicateList.add(criteriaBuilder.greaterThan(criteriaJoinDto.getPerformanceJoin().get("originalPerformanceDateTime"), queryStart));
        predicateList.add(criteriaBuilder.lessThan(criteriaJoinDto.getPerformanceJoin().get("originalPerformanceDateTime"), queryEnd));

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

    public String searchThePath(FilterReportDto filterReportDto) {
        switch (filterReportDto.getFilterByPath()) {
            case "theater" -> {
                Theater theater = this.theaterService.findById(filterReportDto.getSearchId());
                return theater.getTheaterName();
            }
            case "auditorium" -> {
                Auditorium auditorium = this.auditoriumService.findAuditoriumById(filterReportDto.getSearchId());
                return auditorium.getAuditoriumName();
            }
            default -> {
                Play play = this.playService.findById(filterReportDto.getSearchId());
                return play.getPlayName();
            }
        }
    }


}
