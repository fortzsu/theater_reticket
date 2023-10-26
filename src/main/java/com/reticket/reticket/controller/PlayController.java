package com.reticket.reticket.controller;


import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.dto.list.InitFormDataToPlaySaveDto;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.service.AuditoriumService;
import com.reticket.reticket.service.ContributorService;
import com.reticket.reticket.service.PlayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@Controller
@RequestMapping("/api/play")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService playService;
    private final ContributorService contributorService;
    private final AuditoriumService auditoriumService;

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<HttpStatus> save(@RequestBody List<PlaySaveDto> playSaveDtoList) {
        try {
            this.playService.save(playSaveDtoList);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuditoriumNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/formData/{auditoriumId}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<InitFormDataToPlaySaveDto> getPlayFormDataOfAuditoriumAndContributors(
            @PathVariable(value = "auditoriumId") String auditoriumId) {
        InitFormDataToPlaySaveDto initData = new InitFormDataToPlaySaveDto();
        initData.setContributorsList(this.contributorService.findContributorsToPlay());
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(Long.valueOf(auditoriumId));
        if (auditorium != null) {
            initData.setNumberOfPriceCategories(auditorium.getNumberOfPriceCategories());
            return new ResponseEntity<>(initData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<List<ListPlaysDto>> listPlays(@RequestBody PageableDto pageableDto) {
        this.playService.listPlays(pageableDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody UpdatePlayDto updatePlayDto) {
        this.playService.updatePlay(updatePlayDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        this.playService.deletePlay(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
