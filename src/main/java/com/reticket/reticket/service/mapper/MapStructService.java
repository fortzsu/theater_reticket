package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.dto.save.AddressSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MapStructService {

    public abstract AddressEntity createAddressEntityFromDto(AddressSaveDto addressSaveDto);

    public abstract void updateAddressEntityFromDto(@MappingTarget AddressEntity addressEntity, AddressSaveDto addressSaveDto);


}
