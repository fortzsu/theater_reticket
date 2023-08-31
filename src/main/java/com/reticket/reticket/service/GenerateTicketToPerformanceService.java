package com.reticket.reticket.service;

import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenerateTicketToPerformanceService {

    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final PerformanceService performanceService;

    public GenerateTicketToPerformanceService(TicketService ticketService, PerformanceService performanceService) {
        this.ticketService = ticketService;
        this.performanceService = performanceService;
    }

    public boolean generate(List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = this.performanceService.save(performanceSaveDtoList);
        boolean flag = false;
        for (int i = 0; i < performanceSaveDtoList.size(); i++) {
            flag = this.ticketService.searchSeatsForPerformance(performanceList.get(i), performanceSaveDtoList.get(i));
        }
        return flag;
    }
}
