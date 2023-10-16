package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.service.TheaterService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
public class TheaterController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    @RolesAllowed("ROLE_SUPER")
    public ResponseEntity<Void> createTheatre(@RequestBody TheaterSaveDto theatreSaveDto) {
        theaterService.save(theatreSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/list")
    public ResponseEntity<List<ListTheatersDto>> listTheaters(@RequestBody PageableDto pageableDto) {
        this.theaterService.listTheaters(pageableDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RolesAllowed("ROLE_SUPER")
    @PutMapping("/{theaterName}")
    public ResponseEntity<Boolean> updateTheater(@RequestBody TheaterSaveDto theatreSaveDto, @PathVariable String theaterName) {
        boolean isUpdateTheaterOk = this.theaterService.updateTheater(theatreSaveDto, theaterName);
        if(isUpdateTheaterOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RolesAllowed("ROLE_SUPER")
    @DeleteMapping("/{theaterName}")
    public ResponseEntity<Boolean> deleteTheater(@PathVariable String theaterName) {
        boolean isDeleteTheaterOk = this.theaterService.deleteTheater(theaterName);
        if(isDeleteTheaterOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
