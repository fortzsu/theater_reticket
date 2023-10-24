package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.dto.save.AddressSaveDto;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public static AddressEntity addressDtoToEntity(AddressSaveDto addressSaveDto, AddressEntity addressEntity) {
        addressEntity.setCity(addressSaveDto.getCity());
        addressEntity.setHouseNumber(addressSaveDto.getHouseNumber());
        addressEntity.setStreet(addressSaveDto.getStreet());
        addressEntity.setPostCode(addressSaveDto.getPostCode());
        return addressEntity;
    }


}
