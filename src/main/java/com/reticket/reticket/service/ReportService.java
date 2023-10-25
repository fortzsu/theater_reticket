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

    private static final String TICKET_CONDITION = "ticketCondition";
    private static final String ORIGINAL_DATE_TIME = "originalPerformanceDateTime";
    private static final String ID = "id";
    private static final String THEATER = "theater";
    private static final String AUDITORIUM = "auditorium";

    private LocalDateTime searchStartDate;
    private LocalDateTime searchEndDate;


    public ReportResultDto report(FilterReportDto filter) {
        TicketCondition ticketCondition = findTicketCondition(filter.getTicketCondition());
        addValuesToSearchDates(filter);
        ReportResultDto reportResultDto = fillResultQuery(filter, ticketCondition);
        fillReportResultDto(reportResultDto, ticketCondition, filter);
        exportToGoogleSheets(filter, reportResultDto);
        return reportResultDto;
    }

    private void fillReportResultDto(ReportResultDto reportResultDto, TicketCondition ticketCondition, FilterReportDto filter) {
        reportResultDto.setTicketCondition(ticketCondition.getDisplayName());
        reportResultDto.setStart(searchStartDate);
        reportResultDto.setEnd(searchEndDate);
        if (filter.isPerformances()) {
            reportResultDto.setReportResultPerformancesDtos(fillPerformances(filter, ticketCondition));
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

    private ReportResultDto fillResultQuery(FilterReportDto filterReportDto, TicketCondition ticketCondition) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReportResultDto> reportQuery = criteriaBuilder.createQuery(ReportResultDto.class);
        Root<Ticket> ticketRoot = reportQuery.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDto = createJoinDto(ticketRoot);

        return entityManager.createQuery(
                reportQuery.multiselect(
                                criteriaBuilder.count(ticketRoot),
                                criteriaBuilder.sum(criteriaJoinDto.getPriceJoin().get("amount"))).
                        where(fillPredicateList(filterReportDto, ticketRoot, criteriaBuilder, criteriaJoinDto,
                                ticketCondition)
                                .toArray(new Predicate[0]))).getSingleResult();

    }

    private List<ReportResultPerformancesDto> fillPerformances(FilterReportDto filter, TicketCondition ticketCondition) {
        CriteriaBuilder criteriaBuilderPerformances = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReportResultPerformancesDto> criteriaQueryPerformances = criteriaBuilderPerformances.createQuery(ReportResultPerformancesDto.class);
        Root<Ticket> ticketRoot = criteriaQueryPerformances.from(Ticket.class);
        CriteriaJoinDto criteriaJoinDtoPerformances = createJoinDto(ticketRoot);
        return createPerformancesQuery(criteriaQueryPerformances, criteriaJoinDtoPerformances, criteriaBuilderPerformances,
                ticketCondition, ticketRoot, filter);
    }

    private List<ReportResultPerformancesDto> createPerformancesQuery(CriteriaQuery<ReportResultPerformancesDto> criteriaQueryPerformances,
                                                                      CriteriaJoinDto criteriaJoinDtoPerformances, CriteriaBuilder criteriaBuilderPerformances,
                                                                      TicketCondition ticketCondition, Root<Ticket> ticketRoot, FilterReportDto filter) {
        return entityManager.createQuery(
                        criteriaQueryPerformances.multiselect(
                                        criteriaJoinDtoPerformances.getPerformanceJoin().get(ORIGINAL_DATE_TIME),
                                        criteriaBuilderPerformances.count(ticketRoot),
                                        criteriaBuilderPerformances.sum(criteriaJoinDtoPerformances.getPriceJoin().get("amount")),
                                        criteriaJoinDtoPerformances.getTheaterJoin().get("theaterName"),
                                        criteriaJoinDtoPerformances.getAuditoriumJoin().get("auditoriumName"),
                                        criteriaJoinDtoPerformances.getPlayJoin().get("playName")).
                                groupBy(ticketRoot.get("performance")).where(fillPredicateList(filter, ticketRoot,
                                        criteriaBuilderPerformances, criteriaJoinDtoPerformances, ticketCondition).toArray(new Predicate[0]))).
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


}
