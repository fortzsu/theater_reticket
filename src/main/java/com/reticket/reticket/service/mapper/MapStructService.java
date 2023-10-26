package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.dto.save.AddressSaveDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class MapStructService {

        public abstract AddressEntity fromDtoToEntity(AddressSaveDto addressSaveDto);




}
