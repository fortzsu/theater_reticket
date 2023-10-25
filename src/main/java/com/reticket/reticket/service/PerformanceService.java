package com.reticket.reticket.service;

import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.repository.PerformanceRepository;
import com.reticket.reticket.service.mapper.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PlayService playService;
    private final FilteredPerformanceService filteredPerformanceService;

    public List<Performance> save(List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = new ArrayList<>();
        for (PerformanceSaveDto saveDto : performanceSaveDtoList) {
            Performance performance = new Performance();
            Performance updatedPerformance =
                    this.performanceRepository.save(addInitDataForNewPerformance(performance, saveDto));
            performanceList.add(updatedPerformance);
        }
        return performanceList;
    }

    private Performance addInitDataForNewPerformance(Performance performance, PerformanceSaveDto performanceSaveDto) {
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
        return this.filteredPerformanceService.searchFilteredPerformances(dto);
    }

    public void updatePerformance(UpdatePerformanceDto updatePerformanceDto, Long id) {
        Performance performance = this.findPerformanceById(id);
        if (performance != null) {
            MapperService.performanceDtoToEntity(updatePerformanceDto, performance);
        }
    }
}
