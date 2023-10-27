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


    public void save(List<AuditoriumSaveDto> auditoriumSaveDtoList) {
        for (AuditoriumSaveDto dto : auditoriumSaveDtoList) {
            generateSeatsToAuditorium(dto);
        }
    }

    public Auditorium update(AuditoriumSaveDto auditoriumSaveDto, Long id) {
        Optional<Auditorium> opt = auditoriumRepository.findById(id);
        return opt.map(auditorium -> updateValues(auditoriumSaveDto, auditorium)).orElse(null);
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

    public void deleteAuditorium(Long id) {
        Optional<Auditorium> opt = this.auditoriumRepository.findById(id);
        if (opt.isPresent()) {
            Auditorium auditorium = opt.get();
            auditorium.setIsActive(false);
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

    public Auditorium findAuditoriumById(Long auditoriumId) {
        Optional<Auditorium> opt = this.auditoriumRepository.findById(auditoriumId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new AuditoriumNotFoundException("The Auditorium is not found!");
        }
    }

    public Auditorium findAuditoriumByAuditoriumName(String auditoriumName) {
        return this.auditoriumRepository.findAuditoriumByAuditoriumName(auditoriumName);
    }

}
