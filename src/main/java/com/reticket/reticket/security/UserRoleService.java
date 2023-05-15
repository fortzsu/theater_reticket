package com.reticket.reticket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        HashSet<RoleAuthority> authorities = new HashSet<>();
        authorities.add(new RoleAuthority(AuthorityEnum.SUPER_ADMIN));
        authorities.add(new RoleAuthority(AuthorityEnum.RESERVE_BUY_RETURN_LIKE));
        authorities.add(new RoleAuthority(AuthorityEnum.SELL_RESERVE_RESELL_REPORT));
        authorities.add(new RoleAuthority(AuthorityEnum.SELL_RESERVE_RESELL_REPORT_ADD_NEW_ITEMS_BUT_THEATRE));
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.SUPER_ADMIN);
        userRole.setAuthorities(authorities);
        this.userRoleRepository.save(userRole);
    }
}
