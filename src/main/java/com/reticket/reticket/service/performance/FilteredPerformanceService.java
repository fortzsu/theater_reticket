package com.reticket.reticket.service.performance;


import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.enums.PlayType;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FilteredPerformanceService {

    @PersistenceContext
    private EntityManager entityManager;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Page<PerformanceListDto> searchFilteredPerformances(FilterPerformancesDto dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerformanceListDto> criteriaQuery = criteriaBuilder.createQuery(PerformanceListDto.class);
        Root<Performance> performanceRoot = criteriaQuery.from(Performance.class);
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Performance, Play> playJoin = performanceRoot.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);

        Pageable pageable = PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize());

        CriteriaBuilderUtils.createJoins(criteriaJoinDto, playJoin);
        addValueToDates(dto);
        List<PerformanceListDto> result = createQuery(criteriaQuery, performanceRoot, criteriaBuilder, criteriaJoinDto, dto,
                pageable);

        return new PageImpl<>(result, pageable, result.size());
    }

    private void addValueToDates(FilterPerformancesDto dto) {
        startDate = LocalDateTime.of(dto.getSearchDateDto().getStartYear(), dto.getSearchDateDto().getStartMonth(), dto.getSearchDateDto().getStartDay(), 0, 0);
        endDate = LocalDateTime.of(dto.getSearchDateDto().getEndYear(), dto.getSearchDateDto().getEndMonth(), dto.getSearchDateDto().getEndDay(), 23, 59);
    }

    private List<PerformanceListDto> createQuery(CriteriaQuery<PerformanceListDto> criteriaQuery,
                                                 Root<Performance> performanceRoot, CriteriaBuilder criteriaBuilder,
                                                 CriteriaJoinDto criteriaJoinDto, FilterPerformancesDto dto,
                                                 Pageable pageable) {
        return entityManager.createQuery(
                        criteriaQuery.multiselect(
                                performanceRoot.get("originalPerformanceDateTime"),
                                criteriaJoinDto.getPlayJoin().get("playName"),
                                criteriaJoinDto.getTheaterJoin().get("theaterName"),
                                criteriaJoinDto.getAuditoriumJoin().get("auditoriumName")).where(fillPredicateList(dto, performanceRoot,
                                criteriaBuilder, criteriaJoinDto, startDate, endDate)
                                .toArray(new Predicate[0]))).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private List<Predicate> fillPredicateList(FilterPerformancesDto dto, Root<Performance> performanceRoot,
                                              CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto,
                                              LocalDateTime queryStart, LocalDateTime queryEnd) {

        List<Predicate> predicateList = new ArrayList<>();
        searchPath(dto, predicateList, criteriaBuilder, criteriaJoinDto);

        predicateList.add(criteriaBuilder.and(
                criteriaBuilder.isTrue(performanceRoot.get("isAvailableOnline")),
                criteriaBuilder.isTrue(performanceRoot.get("isSeenOnline")),
                criteriaBuilder.isFalse(performanceRoot.get("isCancelled")),
                criteriaBuilder.isFalse(performanceRoot.get("isSold"))));
        predicateList.add(criteriaBuilder.greaterThan(performanceRoot.get("originalPerformanceDateTime"), queryStart));
        predicateList.add(criteriaBuilder.lessThan(performanceRoot.get("originalPerformanceDateTime"), queryEnd));

        return predicateList;
    }

    private void searchPath(FilterPerformancesDto dto, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder, CriteriaJoinDto criteriaJoinDto) {
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
    }
}
