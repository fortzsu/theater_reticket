package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.PlayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "play")
@Getter
@Setter
@NoArgsConstructor
public class Play {
    @Id
    @Column(name = "play_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "play_name")
    private String playName;

    @Column(name = "plot")
    private String plot;

    @OneToMany(mappedBy = "play", fetch = FetchType.EAGER)
    private List<Performance> performances = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "appUser_plays",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "play_id")
    )
    private List<AppUser> appUsers = new ArrayList<>();

    @Column(name = "premiere")
    private LocalDateTime premiere;
    @Column(name = "archived")
    private Boolean isArchived;
    @OneToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;
    @OneToMany(mappedBy = "play")
    private List<Price> prices = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "play_type")
    private PlayType playType;

    public void addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
    }

    public void setPlayType(String playType) {
        this.playType = findPlayType(playType);
    }

    public static PlayType findPlayType(String playType) {
        PlayType result;
        switch (playType) {
            case "Drama" -> result = PlayType.DRAMA;
            case "Tragedy" -> result = PlayType.TRAGEDY;
            case "Political" -> result = PlayType.POLITICAL;
            case "Crime" -> result = PlayType.CRIME;
            case "Comedy" -> result = PlayType.COMEDY;
            default ->  result = PlayType.MUSICAL;
        }
        return result;
    }
}
