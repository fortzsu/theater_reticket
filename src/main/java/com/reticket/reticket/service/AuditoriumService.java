package com.reticket.reticket.service;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theatre;
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
    private final TheatreService theatreService;
    private final SeatService seatService;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository, TheatreService theatreService,
                              SeatService seatService) {
        this.auditoriumRepository = auditoriumRepository;
        this.theatreService = theatreService;
        this.seatService = seatService;
    }

    public List<Auditorium> save(List<AuditoriumSaveDto> auditoriumSaveDtoList) {
        List<Auditorium> auditoriumList =  new ArrayList<>();
        for (AuditoriumSaveDto dto : auditoriumSaveDtoList) {
            Theatre tempTheatre = this.theatreService.findById(dto.getTheatreId());
            Auditorium savedAuditorium = updateValues(dto, new Auditorium());
            auditoriumList.add(savedAuditorium);
            tempTheatre.setCapacity(tempTheatre.getCapacity() + savedAuditorium.getCapacity());
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
            auditorium.setTheatre(theatreService.findById(auditoriumSaveDto.getTheatreId()));
        } else {
            auditorium.setTheatre(null);
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
