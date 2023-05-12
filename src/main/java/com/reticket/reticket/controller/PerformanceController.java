package com.reticket.reticket.controller;


import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.service.PerformanceService;
import com.reticket.reticket.service.SearchPerformanceService;
import com.reticket.reticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    private final TicketService ticketService;

    private final SearchPerformanceService searchPerformanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService,
                                 TicketService ticketService, SearchPerformanceService searchPerformanceService) {
        this.performanceService = performanceService;
        this.ticketService = ticketService;
        this.searchPerformanceService = searchPerformanceService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<PerformanceSaveDto> performanceSaveDtoList) {
        List<Performance> performance = this.performanceService.save(performanceSaveDtoList);
        for (int i = 0; i < performanceSaveDtoList.size(); i++) {
            this.ticketService.generateTicketsToPerformance(performance.get(i).getId(), performanceSaveDtoList.get(i));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<List<PerformanceListDto>> listPerformances(
            @RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.performanceService.listPerformances(pageableDto), HttpStatus.OK);
    }

    @PostMapping("/searchPerformance")
    public ResponseEntity<List<PerformanceListDto>> searchFilteredPerformances (
            @RequestBody FilterPerformancesDto dto) {
        return new ResponseEntity<>(this.searchPerformanceService.searchFilteredPerformances(dto), HttpStatus.OK);
    }

}
