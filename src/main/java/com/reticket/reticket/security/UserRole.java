package com.reticket.reticket.security;

import com.reticket.reticket.domain.AppUser;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(value = EnumType.STRING) //Calls ToString (ROLE Prefix added)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleEnum roleEnum;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RoleAuthority> authorities = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Set<RoleAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleAuthority> authorities) {
        this.authorities = authorities;
    }

}
