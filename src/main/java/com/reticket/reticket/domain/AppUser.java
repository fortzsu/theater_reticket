package com.reticket.reticket.domain;

import com.reticket.reticket.security.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "appuser")
@Getter
@Setter
@NamedQueries(
        @NamedQuery(
                name = AppUser.FIND_BY_EMAIL,
                query = "SELECT au FROM AppUser au " +
                        "LEFT JOIN FETCH au.userRoles roles " +
                        "LEFT JOIN FETCH roles.authorities " +
                        "WHERE au.email = :email"
        )
)
public class AppUser implements UserDetails {

    private static final String ENTITY_NAME = "AppUser";
    public static final String FIND_BY_EMAIL = ENTITY_NAME + "." + "findByEmail";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "user_email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "appUser")
    private List<Ticket> tickets = new ArrayList<>();
    @ManyToMany
    private Set<UserRole> userRoles = new HashSet<>();
    private UUID uuid;
    private boolean isDeleted;
    @OneToOne
    private Theater theater;

    public AppUser() {
        setUuid();
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        setUuid();
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

        List<GrantedAuthority> userRoles = this.userRoles.stream()
                .map(role -> role.getRoleEnum().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        List<GrantedAuthority> userAuthorities = this.userRoles.stream()
                .flatMap(role -> role.getAuthorities().stream()
                        .map(authority -> authority.getAuthorityEnum().toString())
                        .map(SimpleGrantedAuthority::new)).collect(Collectors.toList());

        authorities.addAll(userRoles);
        authorities.addAll(userAuthorities);

        return authorities;
    }


    public void addUserRoles(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

}
