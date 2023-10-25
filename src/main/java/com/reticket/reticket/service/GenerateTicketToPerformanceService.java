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

    public void generateTickets(List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = this.performanceService.save(performanceSaveDtoList);
        for (int i = 0; i < performanceSaveDtoList.size(); i++) {
            this.ticketService.searchSeatsForPerformance(performanceList.get(i), performanceSaveDtoList.get(i));
        }
    }
}
