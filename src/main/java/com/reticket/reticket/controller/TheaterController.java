package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.service.TheaterService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theater")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping
    @RolesAllowed("ROLE_SUPER")
    public ResponseEntity<Void> save(@RequestBody TheaterSaveDto theatreSaveDto) {
        theaterService.save(theatreSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ListWrapper<ListTheatersDto>> list(@RequestBody PageableDto pageableDto) {
        return new ResponseEntity<>(this.theaterService.listTheaters(pageableDto), HttpStatus.OK);
    }

    @RolesAllowed("ROLE_SUPER")
    @PutMapping("/{theaterName}")
    public ResponseEntity<Void> update(@RequestBody TheaterSaveDto theatreSaveDto, @PathVariable String theaterName) {
        this.theaterService.update(theatreSaveDto, theaterName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RolesAllowed("ROLE_SUPER")
    @DeleteMapping("/{theaterName}")
    public ResponseEntity<Void> delete(@PathVariable String theaterName) {
        this.theaterService.delete(theaterName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
