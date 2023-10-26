package com.reticket.reticket.service.mapper;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.save.*;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
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

    public static void contributorDtoToEntity(Contributor contributor, ContributorSaveDto contributorSaveDto) {
        contributor.setFirstName(contributorSaveDto.getFirstName());
        contributor.setLastName(contributorSaveDto.getLastName());
        contributor.setIntroduction(contributorSaveDto.getIntroduction());
    }

    public static void performanceDtoToEntity(UpdatePerformanceDto updatePerformanceDto, Performance performance) {
        performance.setNewDateTime(updatePerformanceDto.getModifiedDateTime());
        performance.setAvailableOnline(updatePerformanceDto.isAvailableOnline());
        performance.setCancelled(updatePerformanceDto.isCancelled());
        performance.setSeenOnline(updatePerformanceDto.isSeenOnline());
    }

    public static void playDtoToEntity(PlaySaveDto playSaveDto, Play play) {
        play.setPlayName(playSaveDto.getPlayName());
        play.setPlot(playSaveDto.getPlot());
        play.setPlayType(playSaveDto.getPlayType());
    }

}
