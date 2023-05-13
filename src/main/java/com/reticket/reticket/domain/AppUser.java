package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.AppUserType;
import com.reticket.reticket.security.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
public class AppUser implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private AppUserType appUserType;
    @Column(name = "username")
    private String username;

    @Column(name = "user_email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();

//    @ManyToMany
//    private Set<UserRole> roles = new HashSet<>();

    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

//        List<GrantedAuthority> userRoles = this.roles.stream()
//                .map(role -> role.getRoleEnum().toString())
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        List<GrantedAuthority> userAuthorities = this.roles.stream()
//                .flatMap(role -> role.getAuthorities().stream()
//                        .map(authority -> authority.getAuthorityEnum().toString())
//                        .map(SimpleGrantedAuthority::new)).collect(Collectors.toList());

//        authorities.addAll(userRoles);
//        authorities.addAll(userAuthorities);

        return authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AppUserType getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(String appUserType) {
        switch (appUserType) {
            case "Super Admin" -> this.appUserType = AppUserType.SUPER_ADMIN;
            case "Theater Admin" -> this.appUserType = AppUserType.THEATRE_ADMIN;
            case "Theater User" -> this.appUserType = AppUserType.THEATRE_USER;
            default -> this.appUserType = AppUserType.GUEST;
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


//    public Set<UserRole> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<UserRole> roles) {
//        this.roles = roles;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
