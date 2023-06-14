package com.reticket.reticket.config.oauth2;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class CustomOidcUserService extends OidcUserService {

    private final AppUserService appUserService;

    @Autowired
    public CustomOidcUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        AppUser appUser = null;
        String email = oidcUser.getAttribute("email");
        try {
            appUser = (AppUser) this.appUserService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            System.out.println("There is no user with the given email: " + email);
            if(saveGuestUser(oidcUser)) {
                System.out.println("NEW USER IS SAVED");
            }
        }
        return new CustomOidcUser(appUser, oidcUser);
    }

    private boolean saveGuestUser(OidcUser oidcUser) {
        AppUserSaveDto appUserSaveDto = new AppUserSaveDto();
        appUserSaveDto.setEmail(oidcUser.getAttribute("email"));
        appUserSaveDto.setUsername(oidcUser.getAttribute("name"));
        appUserSaveDto.setPassword("password");
        return this.appUserService.saveGuest(appUserSaveDto);
    }

}
