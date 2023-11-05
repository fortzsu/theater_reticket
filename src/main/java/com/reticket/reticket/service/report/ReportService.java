package com.reticket.reticket.service.report;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.domain.Ticket;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.ReportResultDto;
import com.reticket.reticket.dto.report_search.ReportResultPerformancesDto;
import com.reticket.reticket.exception.GoogleSheetsExportException;
import com.reticket.reticket.google.GoogleService;
import com.reticket.reticket.service.AuditoriumService;
import com.reticket.reticket.service.PlayService;
import com.reticket.reticket.service.TheaterService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.reticket.reticket.domain.enums.TicketCondition.findTicketCondition;
import static com.reticket.reticket.service.report.ReportConstants.*;


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
    private final ReportQueryService reportQueryService;

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

    private void fillReportResultDto(ReportResultDto result, TicketCondition ticketCondition, FilterReportDto filter) {
        result.setTicketCondition(ticketCondition.getDisplayName());
        result.setStart(searchStartDate);
        result.setEnd(searchEndDate);
        if (filter.isPerformances()) {
            result.setReportResultPerformancesDtos(prepPerformancesQuery(filter, ticketCondition));
        } else {
            result.setSearchPathName(searchPath(filter));
        }
    }

    private void exportToGoogleSheets(FilterReportDto filter, ReportResultDto reportResultDto) {
        if (filter.isExportToSheet()) {
            try {
                this.googleService.exportDataToSheet(reportResultDto);
            } catch (IOException e) {
                throw new GoogleSheetsExportException();
            }
        }
    }

    private void addValuesToSearchDates(FilterReportDto filter) {
        this.searchStartDate = LocalDateTime.of(filter.getSearchDateDto().getStartYear(), filter.getSearchDateDto().getStartMonth(), filter.getSearchDateDto().getStartDay(), 0, 0);
        this.searchEndDate = LocalDateTime.of(filter.getSearchDateDto().getEndYear(), filter.getSearchDateDto().getEndMonth(), filter.getSearchDateDto().getEndDay(), 23, 59);
        reportQueryService.addValuesToSearchDates(this.searchStartDate, this.searchEndDate);
    }


    private ReportResultDto prepReportQuery(FilterReportDto filter, TicketCondition ticketCondition) {
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.reportQuery = this.criteriaBuilder.createQuery(ReportResultDto.class);
        this.reportTicketRoot = this.reportQuery.from(Ticket.class);
        this.criteriaReportJoinDto = reportQueryService.createJoinDto(this.reportTicketRoot);
        return createReportQuery(filter, ticketCondition);
    }

    private ReportResultDto createReportQuery(FilterReportDto filter,
                                              TicketCondition ticketCondition) {
        return this.entityManager.createQuery(
                this.reportQuery.multiselect(
                                this.criteriaBuilder.count(this.reportTicketRoot),
                                this.criteriaBuilder.sum(this.criteriaReportJoinDto.getPriceJoin().get("amount"))).
                        where(reportQueryService.fillPredicateList(filter, this.reportTicketRoot, this.criteriaBuilder, this.criteriaReportJoinDto,
                                        ticketCondition)
                                .toArray(new Predicate[0]))).getSingleResult();
    }


    private List<ReportResultPerformancesDto> prepPerformancesQuery(FilterReportDto filter, TicketCondition ticketCondition) {
        this.criteriaBuilderPerformances = this.entityManager.getCriteriaBuilder();
        this.criteriaQueryPerformances = this.criteriaBuilderPerformances.createQuery(ReportResultPerformancesDto.class);
        this.performancesTicketRoot = this.criteriaQueryPerformances.from(Ticket.class);
        this.criteriaJoinDtoPerformances = reportQueryService.createJoinDto(this.performancesTicketRoot);
        return createPerformancesQuery(ticketCondition, filter);
    }

    private List<ReportResultPerformancesDto> createPerformancesQuery(TicketCondition ticketCondition, FilterReportDto filter) {
        return entityManager.createQuery(
                        this.criteriaQueryPerformances.multiselect(
                                        this.criteriaJoinDtoPerformances.getPerformanceJoin().get(ORIGINAL_DATE_TIME.getDisplayName()),
                                        this.criteriaBuilderPerformances.count(this.performancesTicketRoot),
                                        this.criteriaBuilderPerformances.sum(this.criteriaJoinDtoPerformances.getPriceJoin().get("amount")),
                                        this.criteriaJoinDtoPerformances.getTheaterJoin().get("theaterName"),
                                        this.criteriaJoinDtoPerformances.getAuditoriumJoin().get("auditoriumName"),
                                        this.criteriaJoinDtoPerformances.getPlayJoin().get("playName")).
                                groupBy(this.performancesTicketRoot.get("performance")).
                                where(reportQueryService.fillPredicateList(filter, this.performancesTicketRoot,
                                                this.criteriaBuilderPerformances, this.criteriaJoinDtoPerformances, ticketCondition).
                                        toArray(new Predicate[0]))).
                getResultList();
    }

    public String searchPath(FilterReportDto filter) {
        switch (filter.getFilterByPath()) {
            case "theater" -> {
                Theater theater = this.theaterService.findById(filter.getSearchId());
                return theater.getTheaterName();
            }
            case "auditorium" -> {
                Auditorium auditorium = this.auditoriumService.findAuditoriumById(filter.getSearchId());
                return auditorium.getAuditoriumName();
            }
            default -> {
                Play play = this.playService.findById(filter.getSearchId());
                return play.getPlayName();
            }
        }
    }


}
