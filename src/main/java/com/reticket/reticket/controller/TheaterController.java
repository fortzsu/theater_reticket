package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<Void> createTheatre(@RequestBody TheaterSaveDto theatreSaveDto) {
        theaterService.save(theatreSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/listTheater")
    public ResponseEntity<List<ListTheatersDto>> listTheaters(@RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.theaterService.listTheaters(pageableDto), HttpStatus.OK);
    }

    @PutMapping("/update/{theaterName}")
    public ResponseEntity<Boolean> updateTheater(@RequestBody TheaterSaveDto theatreSaveDto, @PathVariable String theaterName) {
        if(this.theaterService.updateTheater(theatreSaveDto, theaterName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/delete/{theaterName}")
    public ResponseEntity<Boolean> deleteTheater(@PathVariable String theaterName) {
        if(this.theaterService.deleteTheater(theaterName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
