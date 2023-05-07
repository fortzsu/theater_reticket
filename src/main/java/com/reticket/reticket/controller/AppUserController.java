package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/appUser")
public class AppUserController {

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody List<AppUserSaveDto> appUserSaveDtoList) {
        this.appUserService.save(appUserSaveDtoList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/tickets")
    public ResponseEntity<List<ListTicketDto>> listTickets(@PathVariable(value = "username") String username) {
        return new ResponseEntity<>(this.appUserService.listTickets(username),HttpStatus.OK);
    }

    @PostMapping("/{username}/{playId}")
    public ResponseEntity likePlay(@PathVariable(value = "username") String username,
                                   @PathVariable(value = "playId") Long playId) {
        this.appUserService.likePlay(username, playId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/likedPlays")
    public ResponseEntity<List<LikedPlaysListDto>> listLikedPlays(@PathVariable String username) {
        return new ResponseEntity<>(this.appUserService.listLikedPlays(username),HttpStatus.OK);
    }




}
