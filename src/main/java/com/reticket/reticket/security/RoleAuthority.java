package com.reticket.reticket.security;

import jakarta.persistence.*;

@Entity
public class RoleAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "authority_name", nullable = false)
    //unique = true
    private AuthorityEnum authorityEnum;

    public RoleAuthority(AuthorityEnum authorityEnum) {
        this.authorityEnum = authorityEnum;
    }

    public RoleAuthority() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityEnum getAuthorityEnum() {
        return authorityEnum;
    }

    public void setAuthorityEnum(AuthorityEnum authorityEnum) {
        this.authorityEnum = authorityEnum;
    }
}
