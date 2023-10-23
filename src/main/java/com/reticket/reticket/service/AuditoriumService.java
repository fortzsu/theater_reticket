package com.reticket.reticket.service;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.dto.wrapper.ListWrapperDto;
import com.reticket.reticket.dto.wrapper.WrapperDto;
import com.reticket.reticket.exception.AuditoriumNotFoundException;
import com.reticket.reticket.repository.AddressRepository;
import com.reticket.reticket.repository.AuditoriumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


    public List<Auditorium> save(List<AuditoriumSaveDto> auditoriumSaveDtoList) {
        List<Auditorium> auditoriumList = new ArrayList<>();
        for (AuditoriumSaveDto dto : auditoriumSaveDtoList) {
            generateSeatsToAuditorium(dto, auditoriumList);
        }
        return auditoriumList;
    }

    private void generateSeatsToAuditorium(AuditoriumSaveDto dto, List<Auditorium> auditoriumList) {
        Theater tempTheater = this.theaterService.findById(dto.getTheaterId());
        if (tempTheater != null) {
            Auditorium savedAuditorium = updateValues(dto, new Auditorium());
            auditoriumList.add(savedAuditorium);
            tempTheater.setCapacity(tempTheater.getCapacity() + savedAuditorium.getCapacity());
            this.seatService.generateSeats(savedAuditorium, dto);
        }
    }

    public Auditorium update(AuditoriumSaveDto auditoriumSaveDto, Long id) {
        Optional<Auditorium> opt = auditoriumRepository.findById(id);
        Auditorium auditorium;
        if (opt.isPresent()) {
            auditorium = opt.get();
            return updateValues(auditoriumSaveDto, auditorium);
        } else {
            return null; //TODO
        }
    }

    private Auditorium updateValues(AuditoriumSaveDto auditoriumSaveDto, Auditorium auditorium) {
        auditorium.setAuditoriumName(auditoriumSaveDto.getAuditoriumName());
        auditorium.setCapacity(auditoriumSaveDto.getSeatNumberPerAuditoriumRow() * auditoriumSaveDto.getNumberOfRows());
        auditorium.setIsActive(true);
        auditorium.setNumberOfPriceCategories(auditoriumSaveDto.getAuditoriumPriceCategorySaveDtoList().size());
        if (auditoriumSaveDto.getTheaterId() != null) {
            auditorium.setTheater(theaterService.findById(auditoriumSaveDto.getTheaterId()));
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
            auditorium.setIsActive(false);
            return true;
        } else {
            return false;
        }
    }

    public ListWrapperDto listAuditoriums() {
        List<WrapperDto> wrapperDtos = new ArrayList<>(auditoriumRepository.findAllAuditorium());
        ListWrapperDto resultList = new ListWrapperDto();
        resultList.setWrapperList(wrapperDtos);
        for (WrapperDto dto : wrapperDtos) {
            findAddressToAuditorium((AuditoriumListDto) dto);
        }
        return resultList;
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
