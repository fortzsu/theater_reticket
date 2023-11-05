package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.TheaterNotFoundException;
import com.reticket.reticket.service.TheaterService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/theater")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping
    @RolesAllowed("ROLE_SUPER")
    public ResponseEntity<Void> save(@RequestBody TheaterSaveDto theatreSaveDto) {
        theaterService.save(theatreSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<ListWrapper<ListTheatersDto>> list(@RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.theaterService.listTheaters(pageableDto), HttpStatus.OK);
    }

    @RolesAllowed("ROLE_SUPER")
    @PutMapping("/{theaterName}")
    public ResponseEntity<Void> update(@RequestBody TheaterSaveDto theatreSaveDto, @PathVariable String theaterName) {
        try {
            this.theaterService.update(theatreSaveDto, theaterName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TheaterNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RolesAllowed("ROLE_SUPER")
    @DeleteMapping("/{theaterName}")
    public ResponseEntity<Void> delete(@PathVariable String theaterName) {
        try {
            this.theaterService.delete(theaterName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TheaterNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
