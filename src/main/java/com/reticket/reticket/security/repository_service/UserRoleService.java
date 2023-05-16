package com.reticket.reticket.security.repository_service;

import com.reticket.reticket.security.AuthorityEnum;
import com.reticket.reticket.security.RoleAuthority;
import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, RoleAuthorityRepository roleAuthorityRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleAuthorityRepository = roleAuthorityRepository;
        createRoleAuthorities();
        createGuestUserRole();
        createSuperAdminUserRole();
        createTheaterUser();
        createTheaterAdmin();
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

    private void createTheaterAdmin() {
        HashSet<RoleAuthority> authorities = new HashSet<>(fillBasicRoleAuthorities());
        authorities.add(findRoleAuthorityById(8L));
        authorities.add(findRoleAuthorityById(9L));
        authorities.add(findRoleAuthorityById(10L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.THEATRE_ADMIN);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private void createTheaterUser() {
        HashSet<RoleAuthority> authorities = new HashSet<>(fillBasicRoleAuthorities());
        authorities.add(findRoleAuthorityById(8L));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.THEATRE_USER);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }

    private void createGuestUserRole() {
        HashSet<RoleAuthority> authorities = new HashSet<>();
        authorities.add(findRoleAuthorityById(1L));
        authorities.add(findRoleAuthorityById(2L));
        authorities.add(findRoleAuthorityById(3L));
        authorities.add(findRoleAuthorityById(4L));
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
        userRole.setRoleEnum(RoleEnum.SUPER_ADMIN);
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
