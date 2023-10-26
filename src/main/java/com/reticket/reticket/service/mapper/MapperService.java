package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.save.*;
import com.reticket.reticket.security.UserRole;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    private MapperService() {
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

    public static void auditoriumDtoToEntity(Auditorium auditorium, AuditoriumSaveDto auditoriumSaveDto) {
        auditorium.setAuditoriumName(auditoriumSaveDto.getAuditoriumName());
        auditorium.setCapacity(auditoriumSaveDto.getSeatNumberPerAuditoriumRow() * auditoriumSaveDto.getNumberOfRows());
        auditorium.setIsActive(true);
        auditorium.setNumberOfPriceCategories(auditoriumSaveDto.getAuditoriumPriceCategorySaveDtoList().size());
    }

    public static void playDtoToEntity(PlaySaveDto playSaveDto, Play play) {
        play.setPlayName(playSaveDto.getPlayName());
        play.setPlot(playSaveDto.getPlot());
        play.setPlayType(playSaveDto.getPlayType());
        play.setPremiere(playSaveDto.getPremiere());
    }

}
