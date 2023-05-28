package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUser;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/saveGuest")
    public ResponseEntity<Void> saveGuest(@RequestBody AppUserSaveDto appUserSaveDto) {
        if (this.appUserService.save(appUserSaveDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); //TODO
        }
    }

    @PostMapping("/saveAssociate")
    public ResponseEntity<Void> saveAssociate(@RequestBody AppUserSaveDto appUserSaveDto) {
        if (this.appUserService.save(appUserSaveDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); //TODO
        }
    }

    @GetMapping("/{username}/tickets")
    public ResponseEntity<List<ListTicketDto>> listTickets(@PathVariable(value = "username") String username) {
        return new ResponseEntity<>(this.appUserService.listTickets(username), HttpStatus.OK);
    }

    @PostMapping("/{username}/{playId}")
    public ResponseEntity<Void> likePlay(@PathVariable(value = "username") String username,
                                         @PathVariable(value = "playId") Long playId) {
        this.appUserService.likePlay(username, playId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/likedPlays")
    public ResponseEntity<List<LikedPlaysListDto>> listLikedPlays(@PathVariable String username) {
        return new ResponseEntity<>(this.appUserService.listLikedPlays(username), HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<Void> updateAppUser(@RequestBody UpdateAppUser updateAppUser,
                                              @PathVariable String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (this.appUserService.updateAppUser(updateAppUser, context.getAuthentication(), username)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (this.appUserService.deleteUser(username, context.getAuthentication())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
