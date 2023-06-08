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
        this.userId = appUser.getId();
        this.claims = oidcUser.getClaims();
        this.oidcUserInfo = oidcUser.getUserInfo();
        this.oidcIdToken = oidcUser.getIdToken();
        this.attributes = oidcUser.getAttributes();
        this.name = oidcUser.getName();
        this.authorities = appUser.getAuthorities();
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
        return null;
    }

    public Long getUserId() {
        return userId;
    }
}
