package com.reticket.reticket.service.performance;

import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.service.TicketService;
import com.reticket.reticket.service.performance.PerformanceService;
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

    public void generateTickets(PerformanceSaveDto performanceSaveDto) {
        List<Performance> performanceList = this.performanceService.save(performanceSaveDto);
        for (Performance performance : performanceList) {
            this.ticketService.searchSeatsForPerformance(performance, performanceSaveDto);
        }
    }
}
