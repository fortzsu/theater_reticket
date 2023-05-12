package com.reticket.reticket.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theatre")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theatre_name")
    private String theatreName;

    @Column(name = "theatre_history")
    private String theatreHistory;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.EAGER)
    private List<Auditorium> auditoriums = new ArrayList<>();

    public Theatre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
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

    public String getTheatreHistory() {
        return theatreHistory;
    }

    public void setTheatreHistory(String theatreHistory) {
        this.theatreHistory = theatreHistory;
    }
}

