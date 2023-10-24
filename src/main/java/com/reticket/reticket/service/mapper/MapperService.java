package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.security.UserRole;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    private MapperService() {
    }

    public static AddressEntity addressDtoToEntity(AddressSaveDto addressSaveDto, AddressEntity addressEntity) {
        addressEntity.setCity(addressSaveDto.getCity());
        addressEntity.setHouseNumber(addressSaveDto.getHouseNumber());
        addressEntity.setStreet(addressSaveDto.getStreet());
        addressEntity.setPostCode(addressSaveDto.getPostCode());
        return addressEntity;
    }

    public static void appUserDtoToEntity(AppUser appUser, String password, GuestUserSaveDto guestUserSaveDto, UserRole userRole) {
        appUser.setFirstName(guestUserSaveDto.getFirstName());
        appUser.setLastName(guestUserSaveDto.getLastName());
        appUser.setUsername(guestUserSaveDto.getUsername());
        appUser.setEmail(guestUserSaveDto.getEmail());
        appUser.setDeleted(false);
        appUser.setPassword(password);
        appUser.addUserRoles(userRole);
    }


}
