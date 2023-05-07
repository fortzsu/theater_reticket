package com.reticket.reticket.controller;

import com.reticket.reticket.dto.report_search.CriteriaResultDto;
import com.reticket.reticket.dto.report_search.ReportFilterDto;
import com.reticket.reticket.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<CriteriaResultDto> report(@RequestBody ReportFilterDto reportFilterDto) {
        return new ResponseEntity<>(this.reportService.report(reportFilterDto), HttpStatus.OK);
    }


}
