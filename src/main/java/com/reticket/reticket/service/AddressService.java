package com.reticket.reticket.service;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AddressService {


    private final AddressRepository addressRepository;

    private final AuditoriumService auditoriumService;

    private final TheaterService theaterService;


    @Autowired
    public AddressService(AddressRepository addressRepository, AuditoriumService auditoriumService, TheaterService theaterService) {
        this.addressRepository = addressRepository;
        this.auditoriumService = auditoriumService;
        this.theaterService = theaterService;
    }

    public List<AddressEntity> save(List<AddressSaveDto> addressSaveDtoList) {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        for (AddressSaveDto dto : addressSaveDtoList) {
            AddressEntity addressEntity = updateValues(dto, new AddressEntity());
            addressEntityList.add(addressEntity);
            this.addressRepository.save(addressEntity);
        }
        return addressEntityList;
    }

    private AddressEntity updateValues(AddressSaveDto addressSaveDto, AddressEntity addressEntity) {
        addressEntity.setCity(addressSaveDto.getCity());
        addressEntity.setHouseNumber(addressSaveDto.getHouseNumber());
        addressEntity.setStreet(addressSaveDto.getStreet());
        addressEntity.setPostCode(addressSaveDto.getPostCode());
        addressEntity.setAuditoriumId(this.auditoriumService.findAuditoriumById(addressSaveDto.getAuditoriumId()));
        return addressEntity;
    }


    public AddressEntity findById(Long id) {
        Optional<AddressEntity> opt = this.addressRepository.findById(id);
        return opt.orElse(null);
    }

    public AddressEntity findByAuditoriumId(Auditorium auditorium) {
        return this.addressRepository.findByAuditoriumId(auditorium);
    }

    public boolean update(AddressSaveDto addressSaveDto, Long id) {
        Optional<AddressEntity> address = this.addressRepository.findById(id);
        if(address.isPresent()) {
            updateValues(addressSaveDto, address.get());
            return true;
        } else {
            return false;
        }
    }
}
