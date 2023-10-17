package com.reticket.reticket.service;

import com.github.javafaker.App;
import com.reticket.reticket.domain.*;
import com.reticket.reticket.dto.save.AssociateUserSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.security.repository_service.UserRoleRepository;
import com.reticket.reticket.security.repository_service.UserRoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final TheaterService theaterService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, TheaterService theaterService,
                          PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.theaterService = theaterService;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return entityManager.createNamedQuery(AppUser.FIND_BY_EMAIL, AppUser.class).setParameter(
                    "email", email).getSingleResult();
        } catch (NoResultException exception) {
            throw new UsernameNotFoundException("No user found with given email: " + email);
        }
    }

    public boolean saveGuest(GuestUserSaveDto guestUserSaveDto) {
        String username = guestUserSaveDto.getUsername();
        String email = guestUserSaveDto.getEmail();
        if(!checkIfUsernameAndEmailIsTaken(username, email)) {
            this.appUserRepository.save(updateValues(new AppUser(), guestUserSaveDto, "guest", null));
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfUsernameAndEmailIsTaken(String username, String email) {
        AppUser appUserByEmail = this.appUserRepository.findByEmail(email);
        AppUser appUserByUsername = this.findByUsername(username);
        return  appUserByEmail != null && appUserByUsername != null;
    }

    public boolean saveAssociate(AssociateUserSaveDto associateUserSaveDto) {
        String username = associateUserSaveDto.getGuestUserSaveDto().getUsername();
        String email = associateUserSaveDto.getGuestUserSaveDto().getEmail();
        if(!checkIfUsernameAndEmailIsTaken(username, email)) {
            saveNewAppUser(associateUserSaveDto);
            return true;
        } else {
            return false;
        }
    }

    private void saveNewAppUser(AssociateUserSaveDto associateUserSaveDto) {
        String appUserType = checkAppUserType(associateUserSaveDto.isTheaterAdmin());
        this.appUserRepository.save(updateValues(new AppUser(), associateUserSaveDto.getGuestUserSaveDto(),
                appUserType, associateUserSaveDto.getTheaterId()));
    }

    private AppUser updateValues(AppUser appUser, GuestUserSaveDto guestUserSaveDto, String appUserType, Long theaterId) {
        checkIfTheaterIdIsPresent(theaterId, appUser);
        appUser.setFirstName(guestUserSaveDto.getFirstName());
        appUser.setLastName(guestUserSaveDto.getLastName());
        appUser.setPassword(this.passwordEncoder.encode(guestUserSaveDto.getPassword()));
        appUser.setUsername(guestUserSaveDto.getUsername());
        appUser.setEmail(guestUserSaveDto.getEmail());
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(
                UserRoleService.createUserRoleFromString(appUserType)));
        appUser.setDeleted(false);
        return appUser;
    }

    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }

    private void checkIfTheaterIdIsPresent(Long theaterId, AppUser appUser) {
        if (theaterId != null) {
            appUser.setTheater(this.theaterService.findById(theaterId));
        } else {
            appUser.setTheater(null);
        }
    }

    private String checkAppUserType(boolean isTheaterAdmin) {
        if(isTheaterAdmin) {
            return "theater_admin";
        } else {
            return "theater_user";
        }
    }

    public boolean updateAppUser(UpdateAppUserDto updateAppUserDto, Authentication authentication, String username) {
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
