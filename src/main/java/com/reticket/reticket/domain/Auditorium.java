package com.reticket.reticket.domain;

import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "auditorium")
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private String auditoriumName;
    @Column
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;
    @Column
    private Boolean isActive;
    @Column
    private Integer numberOfPriceCategories;
    @Column
    @OneToMany
    private List<Seat> seats;

    public Auditorium() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getNumberOfPriceCategories() {
        return numberOfPriceCategories;
    }

    public void setNumberOfPriceCategories(Integer numberOfPriceCategories) {
        this.numberOfPriceCategories = numberOfPriceCategories;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
