package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/contributor")
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;


    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> save(@RequestBody ContributorSaveDto contributorSaveDto) {
        this.contributorService.save(contributorSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListWrapper<ListDetailedContributorsDto>> listContributors() {
        return new ResponseEntity<>(this.contributorService.listContributors(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@RequestBody ContributorSaveDto contributorSaveDto, @PathVariable Long id) {
        this.contributorService.update(contributorSaveDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
