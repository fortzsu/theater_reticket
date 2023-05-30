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
        HashSet<RoleAuthority> authorities = new HashSet<>();
        RoleAuthority roleAuthority = new RoleAuthority(AuthorityEnum.SELL);
        authorities.add(roleAuthority);
        UserRole userRole = new UserRole();
        userRole.setAuthorities(authorities);
        userRole.setRoleEnum(RoleEnum.SUPER);
        this.userRoleRepository.save(userRole);
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
