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
    }


    private void createSuperAdmin() {
        AppUser appUser = new AppUser();
        appUser.setUsername("super");
        appUser.setPassword(this.passwordEncoder.encode("test"));
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(RoleEnum.SUPER));
        this.appUserRepository.save(appUser);
        appUser.getAuthorities();
    }

    private void createSuperAdminUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.SELL_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_THEATER));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_ADDRESS_AUDITORIUM));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_CONTRIBUTOR_PRICE_PLAY_PERFORMANCE));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_ASSOCIATE));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.SUPER);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterAdminUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.SELL_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_ASSOCIATE));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_ADDRESS_AUDITORIUM));
        authorities.add(new RoleAuthority(AuthorityEnum.CREATE_AND_UPDATE_CONTRIBUTOR_PRICE_PLAY_PERFORMANCE));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.THEATER_ADMIN);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        authorities.add(new RoleAuthority(AuthorityEnum.SELL_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.ACCESS_REPORT));
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.THEATRE_USER);
        this.userRoleRepository.save(userRole);
    }

    private void createGuestUserRole() {
        UserRole userRole = new UserRole();
        HashSet<RoleAuthority> authorities = new HashSet<>(addCoreAuthorities());
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.GUEST);
        this.userRoleRepository.save(userRole);
    }

    private HashSet<RoleAuthority> addCoreAuthorities( ) {
        HashSet<RoleAuthority> authorities = new HashSet<>();
        authorities.add(new RoleAuthority(AuthorityEnum.LIKE_LIST_PLAY));
        authorities.add(new RoleAuthority(AuthorityEnum.RESERVE_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.RETURN_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.BUY_TICKET));
        authorities.add(new RoleAuthority(AuthorityEnum.LIST_TICKETS));
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
