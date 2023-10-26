package com.reticket.reticket.service;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.repository.AddressRepository;
import com.reticket.reticket.service.mapper.MapStructService;
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
public class AddressService {

    private final AddressRepository addressRepository;
    private final AuditoriumService auditoriumService;
    private final MapStructService mapStructService;

    public void save(List<AddressSaveDto> addressSaveDtoList) {
        for (AddressSaveDto dto : addressSaveDtoList) {
            AddressEntity addressEntity = mapStructService.fromDtoToEntity(dto);
            Auditorium auditorium = auditoriumService.findAuditoriumById(dto.getAuditoriumId());
            addressEntity.setAuditorium(auditorium);
            this.addressRepository.save(addressEntity);
        }
    }
    public AddressEntity findById(Long id) {
        Optional<AddressEntity> opt = this.addressRepository.findById(id);
        return opt.orElse(null);
    }

    public AddressEntity findByAuditoriumId(Auditorium auditorium) {
        return this.addressRepository.findAddressByAuditorium(auditorium);
    }

    public void update(AddressSaveDto addressSaveDto, Long id) {
        Optional<AddressEntity> address = this.addressRepository.findById(id);
//        if (address.isPresent()) {
//            this.mapStructService.fromDtoToEntity(addressSaveDto);
//        }
        address.ifPresent(addressEntity -> MapperService.addressDtoToEntity(addressSaveDto, addressEntity));
    }
}
