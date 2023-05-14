package com.reticket.reticket.domain;


import com.reticket.reticket.domain.enums.PlayType;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Play() {
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public LocalDateTime getPremiere() {
        return premiere;
    }

    public void setPremiere(LocalDateTime premiere) {
        this.premiere = premiere;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public void addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
    }

    public PlayType getPlayType() {
        return playType;
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
