package com.reticket.reticket.controller;


import com.reticket.reticket.dto.list.InitFormDataToPlaySaveDto;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
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
    public ResponseEntity<Void> save(@RequestBody PlaySaveDto playSaveDto) {
        try {
            this.playService.save(playSaveDto);
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
        return new ResponseEntity<>(this.playService.fillInitData(auditoriumId), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ListWrapper<ListPlaysDto>> listPlays(@RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.playService.listPlays(pageableDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdatePlayDto updatePlayDto) {
        this.playService.updatePlay(updatePlayDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.playService.deletePlay(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
