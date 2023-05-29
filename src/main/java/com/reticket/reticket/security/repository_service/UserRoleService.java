package com.reticket.reticket.security.repository_service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.repository.AppUserRepository;
import com.reticket.reticket.security.AuthorityEnum;
import com.reticket.reticket.security.RoleAuthority;
import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, RoleAuthorityRepository roleAuthorityRepository,
                           AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.roleAuthorityRepository = roleAuthorityRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        createRoleAuthorities();
        createSuperAdminUserRole();
        createGuestUserRole();
        createTheaterUserRole();
        createTheaterAdminRole();
        createSuperAdmin();
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


    private void createSuperAdmin() {
        AppUser appUser = new AppUser();
        appUser.setUsername("super");
        appUser.setPassword(this.passwordEncoder.encode("test"));
        appUser.addUserRoles(this.userRoleRepository.findUserRoleByRoleEnum(RoleEnum.SUPER));
        AppUser saved = this.appUserRepository.save(appUser);
        for (UserRole userRole : saved.getUserRoles()) {
            System.out.println(userRole.getRoleEnum().name());
        }
        for (GrantedAuthority authority : saved.getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
    }

    private void createRoleAuthorities() {
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.SELL));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.RESELL));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.RESERVE));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.BUY));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.RETURN));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.LIKE_PLAY));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.REPORT));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.ADD_NEW_PLAY_PERFORMANCE_CONTRIBUTOR));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.ADD_NEW_ASSOCIATE));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.ADD_NEW_AUDITORIUM_ADDRESS));
        this.roleAuthorityRepository.save(new RoleAuthority(AuthorityEnum.ADD_NEW_THEATER));
    }

    private void createTheaterAdminRole() {
        Set<RoleAuthority> authorities = new HashSet<>(fillBasicRoleAuthorities());
        authorities.add(findRoleAuthorityById(8L));
        authorities.add(findRoleAuthorityById(9L));
        authorities.add(findRoleAuthorityById(10L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.THEATER_ADMIN);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterUserRole() {
        Set<RoleAuthority> authorities = new HashSet<>(fillBasicRoleAuthorities());
        authorities.add(findRoleAuthorityById(8L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.THEATRE_USER);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private void createGuestUserRole() {
        Set<RoleAuthority> authorities = new HashSet<>();
        authorities.add(findRoleAuthorityById(1L));
        authorities.add(findRoleAuthorityById(2L));
        authorities.add(findRoleAuthorityById(3L));
        authorities.add(findRoleAuthorityById(4L));
        authorities.add(findRoleAuthorityById(12L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.GUEST);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private void createSuperAdminUserRole() {
        HashSet<RoleAuthority> authorities = new HashSet<>(fillBasicRoleAuthorities());
        authorities.add(findRoleAuthorityById(1L));
        authorities.add(findRoleAuthorityById(2L));
        authorities.add(findRoleAuthorityById(7L));
        authorities.add(findRoleAuthorityById(8L));
        authorities.add(findRoleAuthorityById(9L));
        authorities.add(findRoleAuthorityById(10L));
        authorities.add(findRoleAuthorityById(11L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.SUPER);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private RoleAuthority findRoleAuthorityById(Long id) {
        Optional<RoleAuthority> opt = this.roleAuthorityRepository.findById(id);
        return opt.orElse(null);
    }

    private HashSet<RoleAuthority> fillBasicRoleAuthorities() {
        HashSet<RoleAuthority> authorities = new HashSet<>();
        authorities.add(findRoleAuthorityById(3L));
        authorities.add(findRoleAuthorityById(4L));
        authorities.add(findRoleAuthorityById(5L));
        authorities.add(findRoleAuthorityById(6L));
        return authorities;
    }
}
