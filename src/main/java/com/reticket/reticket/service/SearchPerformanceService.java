package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.CriteriaJoinDto;
import com.reticket.reticket.dto.report_search.CriteriaResultDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.repository.PerformanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Root<Performance> root = criteriaQuery.from(Performance.class);
        CriteriaJoinDto criteriaJoinDto = fillCriteriaJoinDto(root);

        return null;
    }

    private CriteriaJoinDto fillCriteriaJoinDto(Root<Performance> root) {
        CriteriaJoinDto criteriaJoinDto = new CriteriaJoinDto();
        Join<Performance, Play> playJoin = root.join("play");
        criteriaJoinDto.setPlayJoin(playJoin);
        Join<Play, Auditorium> auditoriumJoin = playJoin.join("auditorium");
        criteriaJoinDto.setAuditoriumJoin(auditoriumJoin);
        Join<Auditorium, Theater> theaterJoin = auditoriumJoin.join("theater");
        criteriaJoinDto.setTheaterJoin(theaterJoin);
        return criteriaJoinDto;
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
                                this.playService.findPlayType(dto.getPath()), PageRequest.of(dto.getPageableDto().getPage(), dto.getPageableDto().getPageSize()),
                                startDate, endDate));
            }
        }
    }


    private List<PerformanceListDto> mapPerformanceToDto(List<Performance> performances) {
        return performances.stream().map(PerformanceListDto::new).collect(Collectors.toList());
    }


}

