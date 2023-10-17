package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.domain.enums.PlayType;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.repository.PerformanceRepository;
import com.reticket.reticket.utils.CriteriaBuilderUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerformanceService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PerformanceRepository performanceRepository;
    private final PlayService playService;

    @Autowired
    public PerformanceService(PerformanceRepository performanceRepository, PlayService playService) {
        this.performanceRepository = performanceRepository;
        this.playService = playService;
    }

    public List<Performance> save(List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = new ArrayList<>();
        for (PerformanceSaveDto saveDto : performanceSaveDtoList) {
            Performance performance = new Performance();
            Performance updatedPerformance =
                    this.performanceRepository.save(updateValue(performance, saveDto));
            performanceList.add(updatedPerformance);
        }
        return performanceList;
    }

    private Performance updateValue(Performance performance, PerformanceSaveDto performanceSaveDto) {
        Play play = this.playService.findById(performanceSaveDto.getPlayId());
        performance.setOriginalPerformanceDateTime(performanceSaveDto.getPerformanceDateTime().minusHours(1));
        performance.setPlay(play);
        performance.setAvailableOnline(true);
        performance.setCancelled(false);
        performance.setSeenOnline(true);
        return performance;
    }


    public Performance findPerformanceById(Long id) {
        Optional<Performance> opt = this.performanceRepository.findById(id);
        return opt.orElse(null);
    }

    public Page<PerformanceListDto> searchFilteredPerformances(FilterPerformancesDto dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerformanceListDto> criteriaQuery = criteriaBuilder.createQuery(PerformanceListDto.class);
        Root<Performance> performanceRoot = criteriaQuery.from(Performance.class);
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Performance, Play> playJoin = performanceRoot.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);

        Pageable pageable = PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize());

        CriteriaBuilderUtils.createJoins(criteriaJoinDto, playJoin);
        LocalDateTime startDate = LocalDateTime.of(dto.getSearchDateDto().getStartYear(), dto.getSearchDateDto().getStartMonth(), dto.getSearchDateDto().getStartDay(), 0, 0);
        LocalDateTime endDate = LocalDateTime.of(dto.getSearchDateDto().getEndYear(), dto.getSearchDateDto().getEndMonth(), dto.getSearchDateDto().getEndDay(), 23, 59);

        List<PerformanceListDto> result = entityManager.createQuery(
                        criteriaQuery.multiselect(
                                performanceRoot.get("originalPerformanceDateTime"),
                                criteriaJoinDto.getPlayJoin().get("playName"),
                                criteriaJoinDto.getTheaterJoin().get("theaterName"),
                                criteriaJoinDto.getAuditoriumJoin().get("auditoriumName")).where(fillPredicateList(dto, performanceRoot,
                                criteriaBuilder, criteriaJoinDto, startDate, endDate)
                                .toArray(new Predicate[0]))).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(result, pageable, result.size());
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

        predicateList.add(criteriaBuilder.greaterThan(performanceRoot.get("originalPerformanceDateTime"), queryStart));
        predicateList.add(criteriaBuilder.lessThan(performanceRoot.get("originalPerformanceDateTime"), queryEnd));

        return predicateList;
    }

    public boolean updatePerformance(UpdatePerformanceDto updatePerformanceDto, Long id) {
        Performance performance = this.findPerformanceById(id);
        if (performance != null) {
            updatePerformanceData(updatePerformanceDto, performance);
            return true;
        } else {
            return false;
        }
    }

    private void updatePerformanceData(UpdatePerformanceDto updatePerformanceDto, Performance performance) {
        performance.setNewDateTime(updatePerformanceDto.getModifiedDateTime());
        performance.setAvailableOnline(updatePerformanceDto.isAvailableOnline());
        performance.setCancelled(updatePerformanceDto.isCancelled());
        performance.setSeenOnline(updatePerformanceDto.isSeenOnline());
    }


}
