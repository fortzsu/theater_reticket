package com.reticket.reticket.security.repository_service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.security.AuthorityEnum;
import com.reticket.reticket.security.RoleAuthority;
import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository,
                           AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        createSuperAdminUserRole();
        createGuestUserRole();
        createTheaterUserRole();
        createTheaterAdminUserRole();
        createSuperAdmin();
        createTheaterAdmin();
        createTheaterUser();
    }


    private void createSuperAdmin() {
        AppUser appUser = new AppUser();
        appUser.setUsername("super");
        appUser.setPassword(this.passwordEncoder.encode("test"));
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(RoleEnum.SUPER));
        this.appUserRepository.save(appUser);
    }

    private void createTheaterAdmin() {
        AppUser appUser = new AppUser();
        appUser.setUsername("theaterAdmin");
        appUser.setPassword(this.passwordEncoder.encode("test"));
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(RoleEnum.THEATER_ADMIN));
        this.appUserRepository.save(appUser);
    }

    private void createTheaterUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("theaterUser");
        appUser.setPassword(this.passwordEncoder.encode("test"));
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(RoleEnum.THEATRE_USER));
        this.appUserRepository.save(appUser);
    }

    private void createSuperAdminUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS));
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        authorities.add(new RoleAuthority(AuthorityEnum.MODIFY_IN_THEATER));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.SUPER);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterAdminUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        authorities.add(new RoleAuthority(AuthorityEnum.MODIFY_IN_THEATER));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.THEATER_ADMIN);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.THEATRE_USER);
        this.userRoleRepository.save(userRole);
    }

    private void createGuestUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.MODIFY_APPUSER_AND_FOLLOW_ACTIONS));
        authorities.add(new RoleAuthority(AuthorityEnum.LIKE_PLAY));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.GUEST);
        this.userRoleRepository.save(userRole);
    }

    private HashSet<RoleAuthority> addCoreAuthorities( ) {
        HashSet<RoleAuthority> authorities = new HashSet<>();
        authorities.add(new RoleAuthority(AuthorityEnum.FREE_LISTS_AND_SEARCH));
        authorities.add(new RoleAuthority(AuthorityEnum.TICKET_ACTIONS));
        return authorities;
    }

    public static RoleEnum createUserRoleFromString(String userRole) {
        switch (userRole) {
            case "guest" -> {
                return RoleEnum.GUEST;
            }
            case "theater_admin" -> {
                return RoleEnum.THEATER_ADMIN;
            }
            default ->  {
                return RoleEnum.THEATRE_USER;
            }
        }
    }

}
