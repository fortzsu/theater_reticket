package com.reticket.reticket.controller;


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

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<PlaySaveDto> playSaveDto) {
        this.playService.save(playSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/formData/{auditoriumId}")
    public ResponseEntity<InitFormDataToPlaySaveDto> getPlayFormDataOfAuditoriumAndContributors(
            @PathVariable(value = "auditoriumId") String auditoriumId) {
        InitFormDataToPlaySaveDto initData = new InitFormDataToPlaySaveDto();
        initData.setContributorsList(this.contributorService.findContributors());
        initData.setNumberOfPriceCategories(this.auditoriumService.findAuditoriumById(Long.valueOf(auditoriumId)).getNumberOfPriceCategories());
        return new ResponseEntity<>(initData, HttpStatus.CREATED);
    }

    @GetMapping("/listPlays")
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
