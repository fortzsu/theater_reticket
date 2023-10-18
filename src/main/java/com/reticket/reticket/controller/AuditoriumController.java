package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/auditorium")
@RequiredArgsConstructor
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> createAuditorium(@RequestBody List<AuditoriumSaveDto> auditoriumSaveDto) {
        if (auditoriumService.save(auditoriumSaveDto) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> deleteAuditorium(@PathVariable Long id) {
        if (auditoriumService.deleteAuditorium(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> updateAuditorium(@PathVariable Long id, @RequestBody AuditoriumSaveDto auditoriumSaveDto) {
        if (auditoriumService.update(auditoriumSaveDto, id) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuditoriumSaveDto>> listAuditoriums() {
        return new ResponseEntity<>(auditoriumService.listAuditoriums(), HttpStatus.OK);
    }
}
