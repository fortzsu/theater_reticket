package com.reticket.reticket.service;

import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.list.*;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.repository.ContributorRepository;
import com.reticket.reticket.repository.PerformanceRepository;
import com.reticket.reticket.repository.PlayRepository;
import com.reticket.reticket.security.repository_service.UserRoleRepository;
import com.reticket.reticket.security.repository_service.UserRoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

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
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, TicketService ticketService,
                          PerformanceService performanceService, PlayService playService,
                          TheaterService theaterService, AuditoriumService auditoriumService,
                          AddressService addressService, PlayRepository playRepository,
                          ContributorRepository contributorRepository, PerformanceRepository performanceRepository,
                          PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.ticketService = ticketService;
        this.performanceService = performanceService;
        this.playService = playService;
        this.theaterService = theaterService;
        this.auditoriumService = auditoriumService;
        this.addressService = addressService;
        this.playRepository = playRepository;
        this.contributorRepository = contributorRepository;
        this.performanceRepository = performanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }


    public boolean save(AppUserSaveDto appUserSaveDto) {
        if(!this.checkIfUsernameIsTaken(appUserSaveDto.getUsername())) {
            this.appUserRepository.save(updateValues(new AppUser(), appUserSaveDto));
            //If a user is saved, then it will log in automatically
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(saved, null, saved.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return true;
        } else {
            return false;
        }
    }

    private AppUser updateValues(AppUser appUser, AppUserSaveDto appUserSaveDto) {
        appUser.setFirstName(appUserSaveDto.getFirstName());
        appUser.setLastName(appUserSaveDto.getLastName());
        appUser.setPassword(this.passwordEncoder.encode(appUserSaveDto.getPassword()));
        appUser.setUsername(appUserSaveDto.getUsername());
        appUser.setEmail(appUserSaveDto.getEmail());
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum( //TODO Check auth User to save Associate
                UserRoleService.createUserRoleFromString(appUserSaveDto.getAppUserType())));
        appUser.setDeleted(false);
        return appUser;
    }


    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }

    private boolean checkIfUsernameIsTaken(String username) {
        return this.findByUsername(username) != null;
    }

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

    public List<LikedPlaysListDto> listLikedPlays(String username) {
        List<LikedPlaysListDto> returnListOfLikedPlays = new ArrayList<>();
        AppUser appUser = this.appUserRepository.findByUsername(username);
        List<Play> likedPlaysByAppUser = this.playRepository.findPlaysByAppUser(appUser);
        for (Play play : likedPlaysByAppUser) {
            LikedPlaysListDto dto = new LikedPlaysListDto();
            dto.setPlayName(play.getPlayName());
            dto.setPlayPlot(play.getPlot());
            dto.setTheatreName(play.getAuditorium().getTheater().getTheaterName());
            dto.setTheatreAddress(this.addressService.findByAuditoriumId(play.getAuditorium()).toString());
            dto.setListContributorsDto(findContributorsByPlay(play));
            dto.setPerformanceListDtoListToLikedPlays(findPerformancesByPlay(play));
            returnListOfLikedPlays.add(dto);
        }
        return returnListOfLikedPlays;
    }

    private List<PerformanceListToLikedPlaysDto> findPerformancesByPlay(Play play) {
        List<PerformanceListToLikedPlaysDto> resultList = new ArrayList<>();
        List<Performance> performances = this.performanceRepository.findUpcomingPerformancesByPlayId(play);
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

    public List<ListTicketDto> listTickets(String username) {
        List<ListTicketDto> list = new ArrayList<>();
        AppUser appUser = findByUsername(username);
        List<Ticket> tickets = this.ticketService.findTicketByAppUserId(appUser);
        for (Ticket ticket : tickets) {
            Performance performance = this.performanceService.findPerformanceById(ticket.getPerformance().getId());
            Play play = this.playService.findById(performance.getPlay().getId());
            Auditorium auditorium = this.auditoriumService.findAuditoriumById(play.getAuditorium().getId());
            Theater theater = this.theaterService.findById(auditorium.getTheater().getId());
            AddressEntity addressEntity = this.addressService.findByAuditoriumId(auditorium);
            ListTicketDto listTicketDto = new ListTicketDto();
            listTicketDto.setPlayName(play.getPlayName());
            listTicketDto.setTheatreName(theater.getTheaterName());
            listTicketDto.setAuditoriumAddress(addressEntity.getCity() + ", " + addressEntity.getStreet() + " " + addressEntity.getHouseNumber());
            listTicketDto.setPerformanceDateTime(performance.getOriginalPerformanceDateTime().getYear() + ". " + performance.getOriginalPerformanceDateTime().getMonth());
            listTicketDto.setTicketPrice(ticket.getPrice().getAmount());
            listTicketDto.setTicketCondition(ticket.getTicketCondition().getDisplayName());
            list.add(listTicketDto);
        }
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return entityManager.createNamedQuery(AppUser.FIND_BY_USERNAME, AppUser.class).setParameter(
                "username", username).getSingleResult();
    }

    public Boolean updateAppUser(UpdateAppUserDto updateAppUserDto, Authentication authentication, String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) {
            if (username.equals(authentication.getName())) {
                setUpdatedData(appUser, updateAppUserDto);
                return true;
            } else {
                if (authentication.getName().equals("super")) {
                    setUpdatedData(appUser, updateAppUserDto);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private void setUpdatedData(AppUser appUser, UpdateAppUserDto updateAppUserDto) {
        appUser.setEmail(updateAppUserDto.getEmail());
        appUser.setPassword(this.passwordEncoder.encode(updateAppUserDto.getPassword()));
    }

    public boolean deleteUser(String username, Authentication authentication) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) {
            if (username.equals(authentication.getName())) {
                appUser.setDeleted(true);
                return true;
            } else {
                if (authentication.getName().equals("super")) {
                    appUser.setDeleted(true);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
