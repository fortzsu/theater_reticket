package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.service.ContributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> save(@RequestBody List<ContributorSaveDto> contributorSaveDto) {
        this.contributorService.save(contributorSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ListDetailedContributorsDto>> listContributors() {
        this.contributorService.listContributors();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Boolean> update(@RequestBody ContributorSaveDto contributorSaveDto, @PathVariable Long id) {
        boolean isContributorUpdateOk = this.contributorService.update(contributorSaveDto, id);
        if(isContributorUpdateOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
