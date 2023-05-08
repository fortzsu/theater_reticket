package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.AppUserType;
import com.reticket.reticket.security.UserRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser {
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

    @ManyToMany
    private Set<UserRole> roles = new HashSet<>();

    public AppUser() {
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
            case "Super Admin":
                this.appUserType = AppUserType.SUPER_ADMIN;
                break;
            case "Theatre Admin":
                this.appUserType = AppUserType.THEATRE_ADMIN;
                break;
            case "Theatre User":
                this.appUserType = AppUserType.THEATRE_USER;
                break;
            case "Theatre Viewer":
                this.appUserType = AppUserType.THEATRE_VIEWER;
                break;
            default:
                this.appUserType = AppUserType.GUEST;
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
