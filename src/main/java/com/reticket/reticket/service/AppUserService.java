package com.reticket.reticket.service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.dto.save.AssociateUserSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.exception.AppUserNotFoundException;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.security.UserRole;
import com.reticket.reticket.security.repository_service.UserRoleRepository;
import com.reticket.reticket.security.repository_service.UserRoleService;
import com.reticket.reticket.service.mapper.MapperService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserService implements UserDetailsService {


    private final AppUserRepository appUserRepository;
    private final TheaterService theaterService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    @PersistenceContext
    EntityManager entityManager;

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
        if (!checkIfUsernameAndEmailIsTaken(username, email)) {
            this.appUserRepository.save(updateValues(new AppUser(), guestUserSaveDto, "guest", null));
            return true;
        } else {
            return false;
        }
    }

    public boolean saveAssociate(AssociateUserSaveDto associateUserSaveDto) {
        String username = associateUserSaveDto.getGuestUserSaveDto().getUsername();
        String email = associateUserSaveDto.getGuestUserSaveDto().getEmail();
        if (!checkIfUsernameAndEmailIsTaken(username, email)) {
            saveNewAppUser(associateUserSaveDto);
            return true;
        } else {
            return false;
        }
    }

    private void saveNewAppUser(AssociateUserSaveDto associateUserSaveDto) {
        String appUserType = checkAppUserType(associateUserSaveDto.isTheaterAdmin());
        Long theaterId = associateUserSaveDto.getTheaterId();
        AppUser appUser = updateValues(new AppUser(), associateUserSaveDto.getGuestUserSaveDto(),
                appUserType, theaterId);
        this.appUserRepository.save(appUser);
    }

    private AppUser updateValues(AppUser appUser, GuestUserSaveDto guestUserSaveDto, String appUserType, Long theaterId) {
        checkIfTheaterIdIsPresent(theaterId, appUser);
        String password = this.passwordEncoder.encode(guestUserSaveDto.getPassword());
        UserRole userRole = this.userRoleRepository.findUserRoleByRoleEnum(
                UserRoleService.createUserRoleFromString(appUserType));
        MapperService.appUserDtoToEntity(appUser, password, guestUserSaveDto, userRole);
        return appUser;
    }

    private void setUpdatedData(AppUser appUser, UpdateAppUserDto updateAppUserDto) {
        appUser.setEmail(updateAppUserDto.getEmail());
        appUser.setPassword(this.passwordEncoder.encode(updateAppUserDto.getPassword()));
    }

    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }

    private boolean checkIfUsernameAndEmailIsTaken(String username, String email) {
        AppUser appUserByEmail = this.appUserRepository.findByEmail(email);
        AppUser appUserByUsername = this.findByUsername(username);
        return appUserByEmail != null && appUserByUsername != null;
    }

    private void checkIfTheaterIdIsPresent(Long theaterId, AppUser appUser) {
        if (theaterId != null) {
            appUser.setTheater(this.theaterService.findById(theaterId));
        } else {
            appUser.setTheater(null);
        }
    }

    private String checkAppUserType(boolean isTheaterAdmin) {
        if (isTheaterAdmin) {
            return "theater_admin";
        } else {
            return "theater_user";
        }
    }

    public void updateAppUser(UpdateAppUserDto updateAppUserDto, Authentication authentication, String username) {
        AppUser appUser = findAppUser(username);
        if (username.equals(authentication.getName())) {
            setUpdatedData(appUser, updateAppUserDto);
        } else {
            if (authentication.getName().equals("super")) {
                setUpdatedData(appUser, updateAppUserDto);
            }
        }
    }

    public void deleteUser(String username, Authentication authentication) {
        AppUser appUser = findAppUser(username);
        if (username.equals(authentication.getName())) {
            appUser.setDeleted(true);
        } else {
            if (authentication.getName().equals("super")) {
                appUser.setDeleted(true);
            }
        }
    }

    private AppUser findAppUser(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) {
            return appUser;
        } else {
            throw new AppUserNotFoundException();
        }
    }
}
