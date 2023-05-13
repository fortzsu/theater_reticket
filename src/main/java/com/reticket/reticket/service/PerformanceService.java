package com.reticket.reticket.service;

import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PerformanceService {

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
        performance.setPerformanceDateTime(performanceSaveDto.getPerformanceDateTime().minusHours(1));
        performance.setPlay(play);
        performance.setAvailableOnline(true);
        performance.setCancelled(false);
        performance.setSeenOnline(true);
        return performance;
    }

    public List<PerformanceListDto> listPerformances(PageableDto pageableDto) {
        List<Performance> performances = this.performanceRepository.findUpcomingPerformances(
                PageRequest.of(pageableDto.getPage(),pageableDto.getPageSize()));
        return performances.stream().map(PerformanceListDto::new).collect(Collectors.toList());
    }

    public Performance findPerformanceById(Long id) {
        Optional<Performance> opt = this.performanceRepository.findById(id);
        return opt.orElse(null);
    }
}
