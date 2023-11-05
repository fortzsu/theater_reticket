package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@Controller
@RequestMapping("/api/auditorium")
@RequiredArgsConstructor
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> save(@RequestBody AuditoriumSaveDto auditoriumSaveDto) {
        auditoriumService.save(auditoriumSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            auditoriumService.deleteAuditorium(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuditoriumNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AuditoriumSaveDto auditoriumSaveDto) {
        try {
            auditoriumService.update(auditoriumSaveDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuditoriumNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ListWrapper<AuditoriumListDto>> list() {
        return new ResponseEntity<>(auditoriumService.listAuditoriums(), HttpStatus.OK);
    }
}
