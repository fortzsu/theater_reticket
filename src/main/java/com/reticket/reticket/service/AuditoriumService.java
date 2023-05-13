package com.reticket.reticket.service;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.repository.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuditoriumService {
    private final AuditoriumRepository auditoriumRepository;
    private final TheaterService theaterService;
    private final SeatService seatService;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository, TheaterService theaterService,
                              SeatService seatService) {
        this.auditoriumRepository = auditoriumRepository;
        this.theaterService = theaterService;
        this.seatService = seatService;
    }

    public List<Auditorium> save(List<AuditoriumSaveDto> auditoriumSaveDtoList) {
        List<Auditorium> auditoriumList =  new ArrayList<>();
        for (AuditoriumSaveDto dto : auditoriumSaveDtoList) {
            Theater tempTheater = this.theaterService.findById(dto.getTheatreId());
            Auditorium savedAuditorium = updateValues(dto, new Auditorium());
            auditoriumList.add(savedAuditorium);
            tempTheater.setCapacity(tempTheater.getCapacity() + savedAuditorium.getCapacity());
            this.seatService.generateSeats(savedAuditorium, dto);
        }
        return auditoriumList;
    }

    public Auditorium update(AuditoriumSaveDto auditoriumSaveDto, Long id) {
        Optional<Auditorium> opt = auditoriumRepository.findById(id);
        Auditorium auditorium;
        if (opt.isPresent()) {
            auditorium = opt.get();
            return updateValues(auditoriumSaveDto, auditorium);
        } else {
            return null;
        }
    }

    private Auditorium updateValues(AuditoriumSaveDto auditoriumSaveDto, Auditorium auditorium) {
        auditorium.setAuditoriumName(auditoriumSaveDto.getAuditoriumName());
        auditorium.setCapacity(auditoriumSaveDto.getSeatNumberPerAuditoriumRow() * auditoriumSaveDto.getNumberOfRows());
        auditorium.setActive(true);
        auditorium.setNumberOfPriceCategories(auditoriumSaveDto.getAuditoriumPriceCategorySaveDtoList().size());
        if (auditoriumSaveDto.getTheatreId() != null) {
            auditorium.setTheater(theaterService.findById(auditoriumSaveDto.getTheatreId()));
        } else {
            auditorium.setTheater(null);
        }
        this.auditoriumRepository.save(auditorium);
        return auditorium;
    }

    public boolean deleteAuditorium(Long id) {
        Optional<Auditorium> opt = this.auditoriumRepository.findById(id);
        if (opt.isPresent()) {
            Auditorium auditorium = opt.get();
            auditorium.setActive(false);
            return true;
        } else {
            return false;
        }
    }

    public List<AuditoriumSaveDto> listAuditoriums() {
        return auditoriumRepository.findAll().stream().
                map(AuditoriumSaveDto::new).collect(Collectors.toList());
    }//TODO

    public Auditorium findAuditoriumById(Long auditoriumId) {
        Optional<Auditorium> opt = this.auditoriumRepository.findById(auditoriumId);
        return opt.orElse(null);
    }

    public Auditorium findAuditoriumByAuditoriumName(String auditoriumName) {
        return this.auditoriumRepository.findAuditoriumByAuditoriumName(auditoriumName);
    }

}
