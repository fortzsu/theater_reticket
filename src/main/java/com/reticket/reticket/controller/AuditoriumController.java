package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapperDto;
import com.reticket.reticket.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Void> save(@RequestBody List<AuditoriumSaveDto> auditoriumSaveDto) {
        auditoriumService.save(auditoriumSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        auditoriumService.deleteAuditorium(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AuditoriumSaveDto auditoriumSaveDto) {
        auditoriumService.update(auditoriumSaveDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListWrapperDto> list() {
        return new ResponseEntity<>(auditoriumService.listAuditoriums(), HttpStatus.OK);
    }
}
