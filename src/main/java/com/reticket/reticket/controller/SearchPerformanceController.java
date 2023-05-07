package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.PerformanceListDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.service.SearchPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/searchPerformance")
public class SearchPerformanceController {

    private final SearchPerformanceService searchPerformanceService;

    @Autowired
    public SearchPerformanceController(SearchPerformanceService searchPerformanceService) {
        this.searchPerformanceService = searchPerformanceService;
    }

    @PostMapping()
    public ResponseEntity<List<PerformanceListDto>> searchFilteredPerformances (
            @RequestBody FilterPerformancesDto dto) {
        return new ResponseEntity<>(this.searchPerformanceService.searchFilteredPerformances(dto), HttpStatus.OK);
    }

}
