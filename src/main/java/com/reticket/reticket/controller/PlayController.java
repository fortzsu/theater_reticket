package com.reticket.reticket.controller;


import com.reticket.reticket.dto.list.InitFormDataToPlaySaveDto;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
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

    private PlayService playService;
    private ContributorService contributorService;
    private AuditoriumService auditoriumService;

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
    public ResponseEntity<InitFormDataToPlaySaveDto> getPlayFormData(
            @PathVariable(value = "auditoriumId") String auditoriumId) {
        InitFormDataToPlaySaveDto initData = new InitFormDataToPlaySaveDto();
        List<ListContributorsDto> contributors = this.contributorService.findContributors();
        initData.setContributorsList(contributors);
        initData.setNumberOfPriceCategories(this.auditoriumService.findAuditoriumById(Long.valueOf(auditoriumId)).getNumberOfPriceCategories());
        return new ResponseEntity<>(initData, HttpStatus.CREATED);
    }





//    @PostMapping("{playId}/{username}/userLike")
//    public ResponseEntity userLikePlay(@PathVariable(value = "username") String Username,
//                                       @PathVariable(value = "playId") Long playId) {
//        this.
//    }


}
