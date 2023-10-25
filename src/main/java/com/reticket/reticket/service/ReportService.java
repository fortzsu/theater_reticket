package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.ReportResultDto;
import com.reticket.reticket.dto.report_search.ReportResultPerformancesDto;
import com.reticket.reticket.google.GoogleService;
import com.reticket.reticket.utils.CriteriaBuilderUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.reticket.reticket.domain.enums.TicketCondition.findTicketCondition;


@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;
    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;
    private final PlayService playService;
    private final GoogleService googleService;
//    private final ReportQueryService reportQueryService;

    private static final String ORIGINAL_DATE_TIME = "originalPerformanceDateTime";
    private static final String TICKET_CONDITION = "ticketCondition";
    private static final String ID = "id";
    private static final String THEATER = "theater";
    private static final String AUDITORIUM = "auditorium";

    private LocalDateTime searchStartDate;
    private LocalDateTime searchEndDate;

    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<ReportResultDto> reportQuery;
    private Root<Ticket> reportTicketRoot;
    private CriteriaJoinDto criteriaReportJoinDto;

    private CriteriaBuilder criteriaBuilderPerformances;
    private CriteriaQuery<ReportResultPerformancesDto> criteriaQueryPerformances;
    private Root<Ticket> performancesTicketRoot;
    private CriteriaJoinDto criteriaJoinDtoPerformances;



    public ReportResultDto report(FilterReportDto filter) {
        TicketCondition ticketCondition = findTicketCondition(filter.getTicketCondition());
        addValuesToSearchDates(filter);
        ReportResultDto reportResultDto = prepReportQuery(filter, ticketCondition);
        fillReportResultDto(reportResultDto, ticketCondition, filter);
        exportToGoogleSheets(filter, reportResultDto);
        return reportResultDto;
    }


    private void fillReportResultDto(ReportResultDto reportResultDto, TicketCondition ticketCondition, FilterReportDto filter) {
        reportResultDto.setTicketCondition(ticketCondition.getDisplayName());
        reportResultDto.setStart(searchStartDate);
        reportResultDto.setEnd(searchEndDate);
        if (filter.isPerformances()) {
            reportResultDto.setReportResultPerformancesDtos(prepPerformancesQuery(filter, ticketCondition));
        } else {
            reportResultDto.setSearchPathName(searchPath(filter));
        }
    }

    private void exportToGoogleSheets(FilterReportDto filter, ReportResultDto reportResultDto) {
        if (filter.isExportToSheet()) {
            try {
                this.googleService.exportDataToSheet(reportResultDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addValuesToSearchDates(FilterReportDto filter) {
        searchStartDate = LocalDateTime.of(filter.getSearchDateDto().getStartYear(), filter.getSearchDateDto().getStartMonth(), filter.getSearchDateDto().getStartDay(), 0, 0);
        searchEndDate = LocalDateTime.of(filter.getSearchDateDto().getEndYear(), filter.getSearchDateDto().getEndMonth(), filter.getSearchDateDto().getEndDay(), 23, 59);
    }


    private ReportResultDto prepReportQuery(FilterReportDto filter, TicketCondition ticketCondition) {
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.reportQuery = this.criteriaBuilder.createQuery(ReportResultDto.class);
        this.reportTicketRoot = this.reportQuery.from(Ticket.class);
        this.criteriaReportJoinDto = createJoinDto(this.reportTicketRoot);
        return createReportQuery(filter, ticketCondition);
    }

    private ReportResultDto createReportQuery(FilterReportDto filter,
                                              TicketCondition ticketCondition) {
        return this.entityManager.createQuery(
                this.reportQuery.multiselect(
                                this.criteriaBuilder.count(this.reportTicketRoot),
                                this.criteriaBuilder.sum(this.criteriaReportJoinDto.getPriceJoin().get("amount"))).
                        where(fillPredicateList(filter, this.reportTicketRoot, this.criteriaBuilder, this.criteriaReportJoinDto,
                                ticketCondition)
                                .toArray(new Predicate[0]))).getSingleResult();
    }



    private List<ReportResultPerformancesDto> prepPerformancesQuery(FilterReportDto filter, TicketCondition ticketCondition) {
        this.criteriaBuilderPerformances = this.entityManager.getCriteriaBuilder();
        this.criteriaQueryPerformances = this.criteriaBuilderPerformances.createQuery(ReportResultPerformancesDto.class);
        this.performancesTicketRoot = this.criteriaQueryPerformances.from(Ticket.class);
        this.criteriaJoinDtoPerformances = createJoinDto(this.performancesTicketRoot);
        return createPerformancesQuery(ticketCondition, filter);
    }

    private List<ReportResultPerformancesDto> createPerformancesQuery(TicketCondition ticketCondition, FilterReportDto filter) {
        return entityManager.createQuery(
                        this.criteriaQueryPerformances.multiselect(
                                        this.criteriaJoinDtoPerformances.getPerformanceJoin().get(ORIGINAL_DATE_TIME),
                                        this.criteriaBuilderPerformances.count(this.performancesTicketRoot),
                                        this.criteriaBuilderPerformances.sum(this.criteriaJoinDtoPerformances.getPriceJoin().get("amount")),
                                        this.criteriaJoinDtoPerformances.getTheaterJoin().get("theaterName"),
                                        this.criteriaJoinDtoPerformances.getAuditoriumJoin().get("auditoriumName"),
                                        this.criteriaJoinDtoPerformances.getPlayJoin().get("playName")).
                                groupBy(this.performancesTicketRoot.get("performance")).where(fillPredicateList(filter, this.performancesTicketRoot,
                                        this.criteriaBuilderPerformances, this.criteriaJoinDtoPerformances, ticketCondition).toArray(new Predicate[0]))).
                getResultList();
    }

    public String searchPath(FilterReportDto filterReportDto) {
        switch (filterReportDto.getFilterByPath()) {
            case THEATER -> {
                Theater theater = this.theaterService.findById(filterReportDto.getSearchId());
                return theater.getTheaterName();
            }
            case AUDITORIUM -> {
                Auditorium auditorium = this.auditoriumService.findAuditoriumById(filterReportDto.getSearchId());
                return auditorium.getAuditoriumName();
            }
            default -> {
                Play play = this.playService.findById(filterReportDto.getSearchId());
                return play.getPlayName();
            }
        }
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

    private List<Predicate> fillPredicateList(FilterReportDto dto, Root<Ticket> root,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              TicketCondition ticketCondition) {

        List<Predicate> predicateList = new ArrayList<>();
        findPathForPredicate(dto, predicateList, criteriaBuilder, criteriaJoinDto);
        findTicketConditionForPredicate(dto, predicateList, criteriaBuilder, root, ticketCondition);

        predicateList.add(criteriaBuilder.greaterThan(criteriaJoinDto.getPerformanceJoin().get(ORIGINAL_DATE_TIME), searchStartDate));
        predicateList.add(criteriaBuilder.lessThan(criteriaJoinDto.getPerformanceJoin().get(ORIGINAL_DATE_TIME), searchEndDate));

        return predicateList;
    }



    private void findTicketConditionForPredicate(FilterReportDto dto, List<Predicate> predicateList,
                                                 CriteriaBuilder criteriaBuilder, Root<Ticket> root, TicketCondition ticketCondition) {
        if (dto.getTicketCondition().equals("SOLD")) {
            Predicate sold = criteriaBuilder.equal(root.get(TICKET_CONDITION), findTicketCondition(dto.getTicketCondition()));
            Predicate returned = criteriaBuilder.equal(root.get(TICKET_CONDITION), TicketCondition.RETURNED);
            Predicate soldReturned = criteriaBuilder.or(sold, returned);
            predicateList.add(soldReturned);
        } else {
            predicateList.add(criteriaBuilder.equal(root.get(TICKET_CONDITION),
                    ticketCondition));
        }
    }

    private void findPathForPredicate(FilterReportDto dto, List<Predicate> predicateList,
                                      CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto) {
        if (dto.getFilterByPath().equals(THEATER)) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getTheaterJoin().get(ID), dto.getSearchId()));
        } else if (dto.getFilterByPath().equals(AUDITORIUM)) {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getAuditoriumJoin().get(ID), dto.getSearchId()));
        } else {
            predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get(ID), dto.getSearchId()));
        }
    }


}
