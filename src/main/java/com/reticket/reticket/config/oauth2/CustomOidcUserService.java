package com.reticket.reticket.config.oauth2;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
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
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);
        AppUser appUser = (AppUser) this.appUserService.loadUserByUsername(oidcUser.getAttribute("email"));
        System.out.println(appUser.getEmail());
        return new CustomOidcUser(appUser, oidcUser);
    }


}
