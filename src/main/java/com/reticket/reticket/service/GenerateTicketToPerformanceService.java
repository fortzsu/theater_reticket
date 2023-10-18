package com.reticket.reticket.service;

import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenerateTicketToPerformanceService {


    private final TicketService ticketService;
    private final PerformanceService performanceService;

    public boolean generate(List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = this.performanceService.save(performanceSaveDtoList);
        boolean flag = false;
        for (int i = 0; i < performanceSaveDtoList.size(); i++) {
            flag = this.ticketService.searchSeatsForPerformance(performanceList.get(i), performanceSaveDtoList.get(i));
        }
        return flag;
    }
}
