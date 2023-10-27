package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListContributorsDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.list.PerformanceListToLikedPlaysDto;
import com.reticket.reticket.dto.wrapper.ListWrapper;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.repository.ContributorRepository;
import com.reticket.reticket.repository.PerformanceRepository;
import com.reticket.reticket.repository.PlayRepository;
import com.reticket.reticket.service.performance.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserActionService {

    private final AppUserRepository appUserRepository;
    private final TicketService ticketService;
    private final PerformanceService performanceService;
    private final PlayService playService;
    private final TheaterService theaterService;
    private final AuditoriumService auditoriumService;
    private final AddressService addressService;
    private final PlayRepository playRepository;
    private final ContributorRepository contributorRepository;
    private final PerformanceRepository performanceRepository;

    public boolean likePlay(String username, Long playId) {
        AppUser appUser = findByUsername(username);
        if(appUser != null) {
            Play play = this.playService.findById(playId);
            if(play != null) {
                play.addAppUser(appUser);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }

    public ListWrapper<LikedPlaysListDto> listLikedPlays(String username) {
        AppUser appUser = this.appUserRepository.findByUsername(username);
        List<Play> likedPlaysByAppUser = this.playRepository.findPlaysByAppUser(appUser);
        return fillLikedPlayList(likedPlaysByAppUser);
    }

    private ListWrapper<LikedPlaysListDto> fillLikedPlayList(List<Play> likedPlaysByAppUser) {
        ListWrapper<LikedPlaysListDto> returnListOfLikedPlays = new ListWrapper<>();
        for (Play play : likedPlaysByAppUser) {
            LikedPlaysListDto dto = new LikedPlaysListDto();
            dto.setPlayName(play.getPlayName());
            dto.setPlayPlot(play.getPlot());
            dto.setTheatreName(play.getAuditorium().getTheater().getTheaterName());
            dto.setTheatreAddress(this.addressService.findByAuditoriumId(play.getAuditorium()).toString());
            dto.setListContributorsDto(findContributorsByPlay(play));
            dto.setPerformanceListDtoListToLikedPlays(findPerformancesByPlay(play));
            returnListOfLikedPlays.addItem(dto);
        }
        return returnListOfLikedPlays;
    }

    private List<PerformanceListToLikedPlaysDto> findPerformancesByPlay(Play play) {
        List<Performance> performances = this.performanceRepository.findUpcomingPerformancesByPlayId(play);
        return fillPerformanceList(performances);
    }

    private List<PerformanceListToLikedPlaysDto> fillPerformanceList(List<Performance> performances) {
        List<PerformanceListToLikedPlaysDto> resultList = new ArrayList<>();
        for (Performance performance : performances) {
            PerformanceListToLikedPlaysDto dto = new PerformanceListToLikedPlaysDto();
            dto.setPerformanceDateTime(performance.getOriginalPerformanceDateTime());
            resultList.add(dto);
        }
        return resultList;
    }

    private List<ListContributorsDto> findContributorsByPlay(Play play) {
        List<ListContributorsDto> dtoList = new ArrayList<>();
        List<Contributor> contributors = this.contributorRepository.findContributorsByPlay(play.getId());
        for (Contributor contributor : contributors) {
            ListContributorsDto dto = new ListContributorsDto();
            dto.setContributorFirstName(contributor.getFirstName());
            dto.setContributorLastName(contributor.getLastName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ListWrapper<ListTicketDto> listTickets(String username) {
        AppUser appUser = findByUsername(username);
        List<Ticket> tickets = this.ticketService.findTicketByAppUserId(appUser);
        return fillListTicketDto(tickets);
    }

    private ListWrapper<ListTicketDto> fillListTicketDto(List<Ticket> tickets) {
        ListWrapper<ListTicketDto> resultList = new ListWrapper<>();
        for (Ticket ticket : tickets) {
            ListTicketDto listTicketDto = new ListTicketDto();
            addEntitiesToListTicketDto(listTicketDto, ticket);
            listTicketDto.setTicketPrice(ticket.getPrice().getAmount());
            listTicketDto.setTicketCondition(ticket.getTicketCondition().getDisplayName());
            resultList.addItem(listTicketDto);
        }
        return resultList;
    }

    private void addEntitiesToListTicketDto(ListTicketDto listTicketDto, Ticket ticket) {
        Performance performance = this.performanceService.findPerformanceById(ticket.getPerformance().getId());
        Play play = this.playService.findById(performance.getPlay().getId());
        Auditorium auditorium = this.auditoriumService.findAuditoriumById(play.getAuditorium().getId());
        Theater theater = this.theaterService.findById(auditorium.getTheater().getId());
        AddressEntity addressEntity = this.addressService.findByAuditoriumId(auditorium);
        listTicketDto.setPlayName(play.getPlayName());
        listTicketDto.setTheatreName(theater.getTheaterName());
        listTicketDto.setAuditoriumAddress(addressEntity.getCity() + ", " + addressEntity.getStreet() + " " + addressEntity.getHouseNumber());
        listTicketDto.setPerformanceDateTime(performance.getOriginalPerformanceDateTime().getYear() + ". " + performance.getOriginalPerformanceDateTime().getMonth());
    }




}
