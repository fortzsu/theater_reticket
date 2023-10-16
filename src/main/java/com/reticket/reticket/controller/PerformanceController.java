package com.reticket.reticket.controller;


import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.service.GenerateTicketToPerformanceService;
import com.reticket.reticket.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GenerateTicketToPerformanceService generateTicketToPerformanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService,
                                GenerateTicketToPerformanceService generateTicketToPerformanceService) {
        this.performanceService = performanceService;
        this.generateTicketToPerformanceService = generateTicketToPerformanceService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<HttpStatus> save(@RequestBody List<PerformanceSaveDto> performanceSaveDtoList) {
        boolean flag = this.generateTicketToPerformanceService.generate(performanceSaveDtoList);
        if(flag) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PerformanceListDto>> searchFilteredPerformances(
            @RequestBody FilterPerformancesDto dto) {
        this.performanceService.searchFilteredPerformances(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updatePerformance/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Boolean> updatePerformance(@RequestBody UpdatePerformanceDto updatePerformanceDto, @PathVariable Long id) {
        boolean isUpdatePerformanceOk = this.performanceService.updatePerformance(updatePerformanceDto, id);
        if (isUpdatePerformanceOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
