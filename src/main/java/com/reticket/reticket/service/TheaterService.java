package com.reticket.reticket.service;


import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.repository.AddressRepository;
import com.reticket.reticket.repository.AuditoriumRepository;
import com.reticket.reticket.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TheaterService {

    private final TheaterRepository theaterRepository;

    private final AuditoriumRepository auditoriumRepository;

    private final AddressRepository addressRepository;


    @Autowired
    public TheaterService(TheaterRepository theaterRepository, AuditoriumRepository auditoriumRepository, AddressRepository addressRepository) {
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.addressRepository = addressRepository;
    }

    public Theater save(TheaterSaveDto theatreSaveDto) {
        Theater theater = new Theater();
        return updateValues(theater, theatreSaveDto);
    }

    private Theater updateValues(Theater theater, TheaterSaveDto theatreSaveDto) {
        if(checkIfTheatreNameIsNotTaken(theatreSaveDto.getTheatreName())) {
            theater.setTheaterName(theatreSaveDto.getTheatreName());
            theater.setCapacity(0);
            theater.setIsArchived(false);
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

    public List<ListTheatersDto> listTheaters(PageableDto dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getPageSize());
        List<Theater> theaters = this.theaterRepository.findAllTheater(pageRequest);
        List<ListTheatersDto> resultList = new ArrayList<>();
        searchTheaters(theaters, resultList);
        return resultList;
    }

    private void searchTheaters(List<Theater> theaters, List<ListTheatersDto> resultList) {
        for (Theater theater : theaters) {
            ListTheatersDto listTheatersDto = new ListTheatersDto(theater.getTheaterName(), theater.getTheaterStory());
            List<Auditorium> auditoriums = this.auditoriumRepository.findAllByTheater(theater);
            searchTheatersByAuditorium(auditoriums, listTheatersDto);
            resultList.add(listTheatersDto);
        }
    }


    private void searchTheatersByAuditorium(List<Auditorium> auditoriums, ListTheatersDto listTheatersDto) {
        for (Auditorium auditorium : auditoriums) {
            AddressEntity address = this.addressRepository.findByAuditoriumId(auditorium);
            AuditoriumListDto auditoriumListDto = new AuditoriumListDto(auditorium.getAuditoriumName(), address.toString());
            listTheatersDto.addAuditoriumListDtoList(auditoriumListDto);
        }
    }

    public boolean updateTheater(TheaterSaveDto theatreSaveDto, String theaterName) {
        Theater theater = this.theaterRepository.findTheaterByTheaterName(theaterName);
        if(theater != null) {
            this.updateValues(theater, theatreSaveDto);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTheater(String theaterName) {
        Theater theater = this.theaterRepository.findTheaterByTheaterName(theaterName);
        if(theater != null) {
            theater.setIsArchived(true);
            return true;
        } else {
            return false;
        }
    }

}
