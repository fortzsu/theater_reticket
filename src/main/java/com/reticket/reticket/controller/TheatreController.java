package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.TheatreSaveDto;
import com.reticket.reticket.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    private final TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public ResponseEntity createTheatre(@RequestBody TheatreSaveDto theatreSaveDto) {
        theatreService.save(theatreSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
