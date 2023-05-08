package com.reticket.reticket.service;


import com.reticket.reticket.domain.Theatre;
import com.reticket.reticket.dto.save.TheatreSaveDto;
import com.reticket.reticket.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TheatreService {

    private final TheatreRepository theatreRepository;


    @Autowired
    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre save(TheatreSaveDto theatreSaveDto) {
        Theatre theatre = new Theatre();
        return updateValues(theatre, theatreSaveDto);
    }

    private Theatre updateValues(Theatre theatre, TheatreSaveDto theatreSaveDto) {
        if(checkIfTheatreNameIsNotTaken(theatreSaveDto.getTheatreName())) {
            theatre.setTheatreName(theatreSaveDto.getTheatreName());
            theatre.setCapacity(0);
            return theatreRepository.save(theatre);
        } else {
            return null;
        }
    }

    public boolean checkIfTheatreNameIsNotTaken(String theatreName) {
        return this.theatreRepository.findTheatreByTheatreName(theatreName) == null;
    }

    public Theatre findById(Long id) {
        Optional<Theatre> opt = theatreRepository.findById(id);
        return opt.orElse(null);
    }

}
