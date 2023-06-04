package com.reticket.reticket.controller;


import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.dto.list.InitFormDataToPlaySaveDto;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.list.ListPlaysDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
import com.reticket.reticket.service.AuditoriumService;
import com.reticket.reticket.service.ContributorService;
import com.reticket.reticket.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/play")
public class PlayController {

    private final PlayService playService;
    private final ContributorService contributorService;
    private final AuditoriumService auditoriumService;

    @Autowired
    public PlayController(PlayService playService, ContributorService contributorService, AuditoriumService auditoriumService) {
        this.playService = playService;
        this.contributorService = contributorService;
        this.auditoriumService = auditoriumService;
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> save(@RequestBody List<PlaySaveDto> playSaveDtoList) {
        if(this.playService.save(playSaveDtoList)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/formData/{auditoriumId}")
    public ResponseEntity<InitFormDataToPlaySaveDto> getPlayFormDataOfAuditoriumAndContributors(
            @PathVariable(value = "auditoriumId") String auditoriumId) {
        InitFormDataToPlaySaveDto initData = new InitFormDataToPlaySaveDto();
        initData.setContributorsList(this.contributorService.findContributors());
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(Long.valueOf(auditoriumId));
        if(auditorium != null) {
                initData.setNumberOfPriceCategories(auditorium.getNumberOfPriceCategories());
            return new ResponseEntity<>(initData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<List<ListPlaysDto>> listPlays(@RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.playService.listPlays(pageableDto), HttpStatus.OK);
    }

    @PutMapping("/updatePlay/{id}")
    public ResponseEntity<Boolean> updatePlay(@PathVariable Long id, @RequestBody UpdatePlayDto updatePlayDto) {
        if(this.playService.updatePlay(updatePlayDto, id))  {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deletePlay(@PathVariable Long id) {
        if(this.playService.deletePlay(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
