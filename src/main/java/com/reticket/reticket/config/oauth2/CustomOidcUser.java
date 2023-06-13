package com.reticket.reticket.config.oauth2;

import com.reticket.reticket.domain.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;

public class CustomOidcUser implements OidcUser {

    private final Map<String, Object> claims;
    private final OidcUserInfo oidcUserInfo;
    private final OidcIdToken oidcIdToken;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String name;
    private final Long userId;


    public CustomOidcUser(AppUser appUser, OidcUser oidcUser) {
        System.out.println("CUSTOMOIDCUSER");
        if (appUser != null) {
            System.out.println("CUSTOMOIDCUSER IF");
            this.userId = appUser.getId();
            System.out.println("CUSTOMOIDCUSER GETID");
            this.authorities = appUser.getAuthorities();
            System.out.println("CUSTOMOIDCUSER GETAUTHORITIES");
        } else {
            System.out.println("CUSTOMOIDCUSER ELSE");
            this.userId = 1L;
            this.authorities = oidcUser.getAuthorities();
        }
        claims = oidcUser.getClaims();
        oidcUserInfo = oidcUser.getUserInfo();
        oidcIdToken = oidcUser.getIdToken();
        attributes = oidcUser.getAttributes();
        name = oidcUser.getName();
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return "null";
    }

    public Long getUserId() {
        return userId;
    }
}
