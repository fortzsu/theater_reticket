package com.reticket.reticket.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theater_name")
    private String theaterName;

    @Column(name = "theater_story")
    private String theaterStory;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "theater", fetch = FetchType.EAGER)
    private List<Auditorium> auditoriums = new ArrayList<>();

    private Boolean isArchived;

    public Theater() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theatreName) {
        this.theaterName = theatreName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public String getTheaterStory() {
        return theaterStory;
    }

    public void setTheaterStory(String theaterStory) {
        this.theaterStory = theaterStory;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }
}

