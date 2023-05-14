package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.PlayType;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.utils.CriteriaBuilderUtils;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.repository.PerformanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchPerformanceService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PerformanceRepository performanceRepository;

    private final PlayService playService;

    private final TheaterService theaterService;

    private final AuditoriumService auditoriumService;

    @Autowired
    public SearchPerformanceService(PerformanceRepository performanceRepository, PlayService playService,
                                    TheaterService theaterService, AuditoriumService auditoriumService) {
        this.performanceRepository = performanceRepository;
        this.playService = playService;
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
    }

    public List<PerformanceListDto> searchFilteredPerformances_(FilterPerformancesDto dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerformanceListDto> criteriaQuery = criteriaBuilder.createQuery(PerformanceListDto.class);
        Root<Performance> performanceRoot = criteriaQuery.from(Performance.class);
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Performance, Play> playJoin = performanceRoot.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);

        CriteriaBuilderUtils.createJoins(criteriaJoinDto, playJoin);
        LocalDateTime startDate = LocalDateTime.of(dto.getSearchDateDto().getStartYear(), dto.getSearchDateDto().getStartMonth(), dto.getSearchDateDto().getStartDay(), 0, 0);
        LocalDateTime endDate = LocalDateTime.of(dto.getSearchDateDto().getEndYear(), dto.getSearchDateDto().getEndMonth(), dto.getSearchDateDto().getEndDay(), 23, 59);

        List<PerformanceListDto> result = entityManager.createQuery(
                criteriaQuery.multiselect(
                        performanceRoot.get("performanceDateTime"),
                        criteriaJoinDto.getPlayJoin().get("playName"),
                        criteriaJoinDto.getTheaterJoin().get("theaterName"),
                        criteriaJoinDto.getAuditoriumJoin().get("auditoriumName")).where(fillPredicateList(dto, performanceRoot,
                        criteriaBuilder, criteriaJoinDto, startDate, endDate)
                        .toArray(new Predicate[0]))).getResultList();

        return result;
    }

    private List<Predicate> fillPredicateList(FilterPerformancesDto dto, Root<Performance> performanceRoot,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              LocalDateTime queryStart, LocalDateTime queryEnd) {

        List<Predicate> predicateList = new ArrayList<>();

        switch (dto.getPath()) {
            case "theater" ->
                    predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getTheaterJoin().get("id"), dto.getSearchId()));
            case "auditorium" ->
                    predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getAuditoriumJoin().get("id"), dto.getSearchId()));
            case "play" ->
                    predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get("id"), dto.getSearchId()));
            default -> {
                PlayType playType = Play.findPlayType(dto.getPath());
                predicateList.add(criteriaBuilder.equal(criteriaJoinDto.getPlayJoin().get("playType"), playType));
            }
        }
        predicateList.add(criteriaBuilder.and(
                criteriaBuilder.isTrue(performanceRoot.get("isAvailableOnline")),
                criteriaBuilder.isTrue(performanceRoot.get("isSeenOnline")),
                criteriaBuilder.isFalse(performanceRoot.get("isCancelled")),
                criteriaBuilder.isFalse(performanceRoot.get("isSold"))));

        predicateList.add(criteriaBuilder.greaterThan(performanceRoot.get("performanceDateTime"), queryStart));
        predicateList.add(criteriaBuilder.lessThan(performanceRoot.get("performanceDateTime"), queryEnd));

        return predicateList;
    }


    public List<PerformanceListDto> searchFilteredPerformances(FilterPerformancesDto dto) {
        LocalDateTime startDate = LocalDateTime.of(dto.getSearchDateDto().getStartYear(), dto.getSearchDateDto().getStartMonth(), dto.getSearchDateDto().getStartDay(), 0, 0);
        LocalDateTime endDate = LocalDateTime.of(dto.getSearchDateDto().getEndYear(), dto.getSearchDateDto().getEndMonth(), dto.getSearchDateDto().getEndDay(), 23, 59);
        switch (dto.getPath()) {
            case "theatre" -> {
                Theater theater = this.theaterService.findById(dto.getSearchId());
                return mapPerformanceToDto(
                        this.performanceRepository.findUpcomingPerformancesByTheaterId(
                                theater, PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize()),
                                startDate, endDate));
            }
            case "auditorium" -> {
                Auditorium auditorium = this.auditoriumService.findAuditoriumById(dto.getSearchId());
                return mapPerformanceToDto(
                        this.performanceRepository.findUpcomingPerformancesByAuditoriumId(
                                auditorium, PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize()),
                                startDate, endDate));
            }
            case "play" -> {
                Play play = this.playService.findById(dto.getSearchId());
                return mapPerformanceToDto(
                        this.performanceRepository.findUpcomingPerformancesByPlayIdPage(
                                play, PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize()),
                                startDate, endDate));
            }
            default -> {
                return mapPerformanceToDto(
                        this.performanceRepository.findUpcomingPerformancesByPlayType(
                                this.playService.findPlayType(dto.getPath()),
                                PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize()),
                                startDate, endDate));
            }
        }
    }


    private List<PerformanceListDto> mapPerformanceToDto(List<Performance> performances) {
        return performances.stream().map(PerformanceListDto::new).collect(Collectors.toList());
    }


}

