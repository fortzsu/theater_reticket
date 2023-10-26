package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Contributor;
import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.dto.save.ContributorSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class MapStructService {

    public abstract AddressEntity createAddressEntityFromDto(AddressSaveDto addressSaveDto);
    public abstract void updateAddressEntityFromDto(@MappingTarget AddressEntity addressEntity, AddressSaveDto addressSaveDto);
    public abstract Contributor createContributorEntityFromDto(ContributorSaveDto contributorSaveDto);
    public abstract void updateContributorEntityFromDto(@MappingTarget Contributor contributor, ContributorSaveDto contributorSaveDto);
    @Mapping(target = "originalPerformanceDateTime", ignore = true)
    @Mapping(target = "newDateTime", source = "updatePerformanceDto.modifiedDateTime")
    public abstract void updatePerformanceEntityFromDto(@MappingTarget Performance performance, UpdatePerformanceDto updatePerformanceDto);


}
