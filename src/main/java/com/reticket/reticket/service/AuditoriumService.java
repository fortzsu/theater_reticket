package com.reticket.reticket.service;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.repository.AddressRepository;
import com.reticket.reticket.repository.AuditoriumRepository;
import com.reticket.reticket.service.mapper.MapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditoriumService {


    private final AuditoriumRepository auditoriumRepository;
    private final TheaterService theaterService;
    private final SeatService seatService;
    private final AddressRepository addressRepository;


    public void save(AuditoriumSaveDto auditoriumSaveDto) {
        generateSeatsToAuditorium(auditoriumSaveDto);
    }

    public void update(AuditoriumSaveDto auditoriumSaveDto, Long id) {
        Auditorium auditorium = findAuditoriumById(id);
        updateValues(auditoriumSaveDto, auditorium);
    }

    public void deleteAuditorium(Long auditoriumId) {
        Auditorium auditorium = findAuditoriumById(auditoriumId);
        auditorium.setIsActive(false);
    }

    private Auditorium updateValues(AuditoriumSaveDto auditoriumSaveDto, Auditorium auditorium) {
        MapperService.auditoriumDtoToEntity(auditorium, auditoriumSaveDto);
        setTheaterToAuditorium(auditorium, auditoriumSaveDto);
        return this.auditoriumRepository.save(auditorium);
    }

    private void setTheaterToAuditorium(Auditorium auditorium, AuditoriumSaveDto auditoriumSaveDto) {
        if (auditoriumSaveDto.getTheaterId() != null) {
            auditorium.setTheater(theaterService.findById(auditoriumSaveDto.getTheaterId()));
        } else {
            auditorium.setTheater(null);
        }
    }

    private void generateSeatsToAuditorium(AuditoriumSaveDto dto) {
        Theater theater = this.theaterService.findById(dto.getTheaterId());
        if (theater != null) {
            Auditorium savedAuditorium = updateValues(dto, new Auditorium());
            theater.setCapacity(theater.getCapacity() + savedAuditorium.getCapacity());
            this.seatService.generateSeats(savedAuditorium, dto);
        }
    }

    public Auditorium findAuditoriumById(Long auditoriumId) {
        Optional<Auditorium> opt = this.auditoriumRepository.findById(auditoriumId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new AuditoriumNotFoundException();
        }
    }

    public ListWrapper<AuditoriumListDto> listAuditoriums() {
        List<AuditoriumListDto> resultList = auditoriumRepository.findAllAuditorium();
        ListWrapper<AuditoriumListDto> listWrapper = new ListWrapper<>();
        for (AuditoriumListDto dto : resultList) {
            findAddressToAuditorium(dto);
            listWrapper.addItem(dto);
        }
        return listWrapper;
    }

    private void findAddressToAuditorium(AuditoriumListDto dto) {
        Long auditoriumId = dto.getAuditoriumId();
        Auditorium auditorium = findAuditoriumById(auditoriumId);
        AddressEntity addressEntity = this.addressRepository.findAddressByAuditorium(auditorium);
        dto.setAuditoriumAddress(addressEntity.toString());
    }

    public Auditorium findAuditoriumByAuditoriumName(String auditoriumName) {
        Auditorium auditorium = this.auditoriumRepository.findAuditoriumByAuditoriumName(auditoriumName);
        if(auditorium != null) {
            return auditorium;
        } else {
            throw new AuditoriumNotFoundException();
        }
    }

}
