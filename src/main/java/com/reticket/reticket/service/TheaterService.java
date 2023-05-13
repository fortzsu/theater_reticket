package com.reticket.reticket.service;


import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.ListTheatresDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TheaterService {

    private final TheaterRepository theaterRepository;


    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater save(TheaterSaveDto theatreSaveDto) {
        Theater theater = new Theater();
        return updateValues(theater, theatreSaveDto);
    }

    private Theater updateValues(Theater theater, TheaterSaveDto theatreSaveDto) {
        if(checkIfTheatreNameIsNotTaken(theatreSaveDto.getTheatreName())) {
            theater.setTheaterName(theatreSaveDto.getTheatreName());
            theater.setCapacity(0);
            return theaterRepository.save(theater);
        } else {
            return null;
        }
    }

    public boolean checkIfTheatreNameIsNotTaken(String theatreName) {
        return this.theaterRepository.findTheaterByTheaterName(theatreName) == null;
    }

    public Theater findById(Long id) {
        Optional<Theater> opt = theaterRepository.findById(id);
        return opt.orElse(null);
    }

    public List<ListTheatresDto> listTheatres(PageableDto pageableDto) {
        return null;
    }
}
