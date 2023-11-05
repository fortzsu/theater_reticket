package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.save.AssociateUserSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.AppUserNotFoundException;
import com.reticket.reticket.exception.TheaterNotFoundException;
import com.reticket.reticket.service.AppUserActionService;
import com.reticket.reticket.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/appUser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    private final AppUserActionService appUserActionService;

    @PostMapping("/saveGuest")
    public ResponseEntity<Void> saveGuest(@RequestBody GuestUserSaveDto guestUserSaveDto) {
        try {
            this.appUserService.saveGuest(guestUserSaveDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TheaterNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveAssociate")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> saveAssociate(@RequestBody AssociateUserSaveDto associateUserSaveDto) {
        try {
            this.appUserService.saveAssociate(associateUserSaveDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TheaterNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/tickets")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<ListWrapper<ListTicketDto>> listTickets(@PathVariable(value = "username") String username) {
        return new ResponseEntity<>(this.appUserActionService.listTickets(username), HttpStatus.OK);
    }

    @PostMapping("/{username}/{playId}")
    @PreAuthorize("hasAuthority('LIKE_PLAY')")
    public ResponseEntity<Void> likePlay(@PathVariable(value = "username") String username,
                                         @PathVariable(value = "playId") Long playId) {
        this.appUserActionService.likePlay(username, playId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/likedPlays")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<ListWrapper<LikedPlaysListDto>> listLikedPlays(@PathVariable String username) {
        return new ResponseEntity<>(this.appUserActionService.listLikedPlays(username), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<Void> updateAppUser(@RequestBody UpdateAppUserDto updateAppUserDto,
                                              @PathVariable String username) {
        try {
            this.appUserService.updateAppUser(updateAppUserDto, SecurityContextHolder.getContext().getAuthentication(), username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppUserNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            this.appUserService.deleteUser(username, context.getAuthentication());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppUserNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
