package com.reticket.reticket.controller;


import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.service.ContributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> save(@RequestBody List<ContributorSaveDto> contributorSaveDto) {
        this.contributorService.save(contributorSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ListDetailedContributorsDto>> listContributors() {
        return new ResponseEntity<>(this.contributorService.listContributors(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ContributorSaveDto contributorSaveDto, @PathVariable Long id) {
        if(this.contributorService.update(contributorSaveDto, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
