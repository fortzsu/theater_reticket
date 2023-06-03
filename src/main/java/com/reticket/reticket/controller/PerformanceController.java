package com.reticket.reticket.controller;


import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.service.PerformanceService;
import com.reticket.reticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    private final TicketService ticketService;


    @Autowired
    public PerformanceController(PerformanceService performanceService,
                                 TicketService ticketService) {
        this.performanceService = performanceService;
        this.ticketService = ticketService;
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> save(@RequestBody List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performanceList = this.performanceService.save(performanceSaveDtoList);
        if(!performanceList.isEmpty()) {
            if(this.performanceService.generateTickets(performanceList, performanceSaveDtoList, this.ticketService)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PerformanceListDto>> searchFilteredPerformances (
            @RequestBody FilterPerformancesDto dto) {
        return new ResponseEntity<>(this.performanceService.searchFilteredPerformances(dto), HttpStatus.OK);
    }

    @PostMapping("/updatePerformance/{id}")
    public ResponseEntity<Boolean> updatePerformance(@RequestBody UpdatePerformanceDto updatePerformanceDto, @PathVariable Long id) {
        if(this.performanceService.updatePerformance(updatePerformanceDto, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
