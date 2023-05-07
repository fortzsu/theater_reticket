package com.reticket.reticket.controller;


import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.service.ContributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/contributor")
public class ContributorController {

    private final ContributorService contributorService;

    @Autowired
    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<ContributorSaveDto> contributorSaveDto) {
        this.contributorService.save(contributorSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
