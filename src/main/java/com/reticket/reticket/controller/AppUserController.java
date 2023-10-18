package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.save.AssociateUserSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.service.AppUserActionService;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;

    private final AppUserActionService appUserActionService;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserActionService appUserActionService) {
        this.appUserService = appUserService;
        this.appUserActionService = appUserActionService;
    }

    @PostMapping("/saveGuest")
    public ResponseEntity<Void> saveGuest(@RequestBody GuestUserSaveDto guestUserSaveDto) {
        if (this.appUserService.saveGuest(guestUserSaveDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/saveAssociate")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> saveAssociate( @RequestBody AssociateUserSaveDto associateUserSaveDto) {
        if (this.appUserService.saveAssociate(associateUserSaveDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{username}/tickets")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<List<ListTicketDto>> listTickets(@PathVariable(value = "username") String username) {
        return new ResponseEntity<>(this.appUserActionService.listTickets(username), HttpStatus.OK);
    }

    @PostMapping("/{username}/{playId}")
    @PreAuthorize("hasAuthority('LIKE_PLAY')")
    public ResponseEntity<Void> likePlay(@PathVariable(value = "username") String username,
                                         @PathVariable(value = "playId") Long playId) {
        boolean isLikePlayOk = this.appUserActionService.likePlay(username, playId);
        if(isLikePlayOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/likedPlays")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<List<LikedPlaysListDto>> listLikedPlays(@PathVariable String username) {
        return new ResponseEntity<>(this.appUserActionService.listLikedPlays(username), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<Void> updateAppUser(@RequestBody UpdateAppUserDto updateAppUserDto,
                                              @PathVariable String username) {
        boolean isUpdateUserOk = this.appUserService.updateAppUser(updateAppUserDto, SecurityContextHolder.getContext().getAuthentication(), username);
        if (isUpdateUserOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('MODIFY_APPUSER_AND_FOLLOW_ACTIONS')")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        boolean isDeleteUserOk = this.appUserService.deleteUser(username, context.getAuthentication());
        if (isDeleteUserOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
