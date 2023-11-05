package com.reticket.reticket.controller;


import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import com.reticket.reticket.exception.PerformanceNotFoundException;
import com.reticket.reticket.exception.PlayNotFoundException;
import com.reticket.reticket.service.performance.GenerateTicketToPerformanceService;
import com.reticket.reticket.service.performance.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;
    private final GenerateTicketToPerformanceService generateTicketToPerformanceService;

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> save(@RequestBody PerformanceSaveDto performanceSaveDto) {
        try {
            this.generateTicketToPerformanceService.generateTickets(performanceSaveDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PlayNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PerformanceListDto>> searchFilteredPerformances(
            @RequestBody FilterPerformancesDto dto) {
        return new ResponseEntity<>(this.performanceService.searchFilteredPerformances(dto), HttpStatus.OK);
    }

    @PostMapping("/updatePerformance/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@RequestBody UpdatePerformanceDto updatePerformanceDto, @PathVariable Long id) {
        try {
            this.performanceService.updatePerformance(updatePerformanceDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PerformanceNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
