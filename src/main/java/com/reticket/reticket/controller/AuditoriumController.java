package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/auditorium")
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @PostMapping
    public ResponseEntity createAuditorium(@RequestBody List<AuditoriumSaveDto> auditoriumSaveDto) {
        auditoriumService.save(auditoriumSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuditorium(@PathVariable Long id) {
        if(auditoriumService.deleteAuditorium(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAuditorium(@PathVariable Long id, @RequestBody AuditoriumSaveDto auditoriumSaveDto) {
        if(auditoriumService.update(auditoriumSaveDto, id) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuditoriumSaveDto>> listAuditoriums() {
        return new ResponseEntity<>(auditoriumService.listAuditoriums(), HttpStatus.OK);
    }
}
