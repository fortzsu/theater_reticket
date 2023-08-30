package com.reticket.reticket.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "performance")
public class Performance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "performance_date_time")
    private LocalDateTime originalPerformanceDateTime;

    @Column(name = "online_availability")
    private boolean isAvailableOnline;

    @Column(name = "online_visibility")
    private boolean isSeenOnline;

    @Column(name = "sold_out")
    private boolean isSold;

    @Column(name = "cancelled")
    private boolean isCancelled;

    @Column(name = "newDateTime")
    private LocalDateTime newDateTime;

    @ManyToOne
    @JoinColumn(name = "play_id")
    private Play play;

    @OneToMany(mappedBy = "performance")
    private List<Ticket> tickets = new ArrayList<>();

    public Performance() {
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public boolean isAvailableOnline() {
        return isAvailableOnline;
    }

    public void setAvailableOnline(boolean availableOnline) {
        isAvailableOnline = availableOnline;
    }

    public boolean isSeenOnline() {
        return isSeenOnline;
    }

    public void setSeenOnline(boolean seenOnline) {
        isSeenOnline = seenOnline;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public LocalDateTime getOriginalPerformanceDateTime() {
        return originalPerformanceDateTime;
    }

    public void setOriginalPerformanceDateTime(LocalDateTime performanceDateTime) {
        this.originalPerformanceDateTime = performanceDateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDateTime getNewDateTime() {
        return newDateTime;
    }

    public void setNewDateTime(LocalDateTime newDateTime) {
        this.newDateTime = newDateTime;
    }
}
