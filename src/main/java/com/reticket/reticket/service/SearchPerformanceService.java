package com.reticket.reticket.service;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.repository.PerformanceRepository;
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

