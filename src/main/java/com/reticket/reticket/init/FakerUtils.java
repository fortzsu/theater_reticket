package com.reticket.reticket.init;

import com.reticket.reticket.domain.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class FakerUtils {


    public static List<AppUser> generateInitData(int number, PasswordEncoder passwordEncoder) {
        List<AppUser> appUsers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            AppUser appUser = new AppUser();
            appUser.setUsername("user");
            appUser.setPassword(passwordEncoder.encode("test"));
//            UserRole userRole = new UserRole();
//            Set<RoleAuthority> roleAuthorities = new HashSet<>();
//            RoleAuthority roleAuthority = new RoleAuthority();
//            roleAuthority.setAuthorityEnum(AuthorityEnum.SUPER_ADMIN);
//            roleAuthorities.add(roleAuthority);
//            userRole.setAuthorities(roleAuthorities);
//            Set<UserRole> userRoles = new HashSet<>();
//            userRoles.add(userRole);
//            appUser.setRoles(userRoles);
            System.out.println("USER CREDENTIAL: " + appUser.getUsername() + " : " + appUser.getPassword());
            appUsers.add(appUser);
        }
        return appUsers;
    }
}
