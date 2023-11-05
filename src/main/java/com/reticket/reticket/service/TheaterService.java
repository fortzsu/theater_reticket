package com.reticket.reticket.service;


import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.list.ListTheatersDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.TheaterNotFoundException;
import com.reticket.reticket.repository.AddressRepository;
import com.reticket.reticket.repository.AuditoriumRepository;
import com.reticket.reticket.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final AddressRepository addressRepository;

    public void save(TheaterSaveDto theatreSaveDto) {
        updateValues(new Theater(), theatreSaveDto);
    }

    private void updateValues(Theater theater, TheaterSaveDto theatreSaveDto) {
        if (checkIfTheatreNameIsNotTaken(theatreSaveDto.getTheatreName())) {
            theater.setTheaterName(theatreSaveDto.getTheatreName());
            theater.setCapacity(0);
            theater.setIsArchived(false);
            theaterRepository.save(theater);
        }
    }

    public boolean checkIfTheatreNameIsNotTaken(String theatreName) {
        return this.theaterRepository.findTheaterByTheaterName(theatreName) == null;
    }

    public Theater findById(Long id) {
        Optional<Theater> opt = theaterRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new TheaterNotFoundException();
        }
    }

    public ListWrapper<ListTheatersDto> listTheaters(PageableDto dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getPageSize());
        List<Theater> theaters = this.theaterRepository.findAllTheater(pageRequest);
        ListWrapper<ListTheatersDto> result = new ListWrapper<>();
        result.addAll(searchTheatersToList(theaters, new ArrayList<>()));
        return result;
    }

    private List<ListTheatersDto> searchTheatersToList(List<Theater> theaters, List<ListTheatersDto> resultList) {
        for (Theater theater : theaters) {
            ListTheatersDto listTheatersDto = new ListTheatersDto(theater.getTheaterName(), theater.getTheaterStory());
            List<Auditorium> auditoriums = this.auditoriumRepository.findAllByTheater(theater);
            searchTheatersByAuditorium(auditoriums, listTheatersDto);
            resultList.add(listTheatersDto);
        }
        return resultList;
    }

    private void searchTheatersByAuditorium(List<Auditorium> auditoriums, ListTheatersDto listTheatersDto) {
        for (Auditorium auditorium : auditoriums) {
            AddressEntity address = this.addressRepository.findAddressByAuditorium(auditorium);
            AuditoriumListDto auditoriumListDto = new AuditoriumListDto(auditorium.getAuditoriumName(), address);
            listTheatersDto.addAuditoriumListDtoList(auditoriumListDto);
        }
    }

    public void update(TheaterSaveDto theatreSaveDto, String theaterName) {
        Theater theater = findTheaterByTheaterName(theaterName);
        this.updateValues(theater, theatreSaveDto);
    }

    public void delete(String theaterName) {
        Theater theater = findTheaterByTheaterName(theaterName);
        theater.setIsArchived(true);
    }

    private Theater findTheaterByTheaterName(String theaterName) {
        Theater theater = this.theaterRepository.findTheaterByTheaterName(theaterName);
        if (theater != null) {
            return theater;
        } else {
            throw new TheaterNotFoundException();
        }
    }

}
