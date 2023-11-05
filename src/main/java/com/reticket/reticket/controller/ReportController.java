package com.reticket.reticket.controller;

import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.ReportResultDto;
import com.reticket.reticket.exception.GoogleSheetsExportException;
import com.reticket.reticket.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasAuthority('ACCESS_REPORT')")
    public ResponseEntity<ReportResultDto> report(@RequestBody FilterReportDto filterReportDto) {
        try {
            return new ResponseEntity<>(this.reportService.report(filterReportDto), HttpStatus.OK);
        } catch (GoogleSheetsExportException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
