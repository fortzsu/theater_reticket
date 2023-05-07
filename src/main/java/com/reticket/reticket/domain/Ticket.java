package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.TicketCondition;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Seat seat;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_condition")
    private TicketCondition ticketCondition;
    @ManyToOne
    private Performance performance;
    @OneToOne
    private Price price;
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
    @Column(name = "sold_at")
    private LocalDateTime soldAt;
    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;
    @ManyToOne
    private AppUser appUser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }


    public TicketCondition getTicketCondition() {
        return ticketCondition;
    }

    public void setTicketCondition(TicketCondition ticketCondition) {
        this.ticketCondition = ticketCondition;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public LocalDateTime getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(LocalDateTime soldAt) {
        this.soldAt = soldAt;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(LocalDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
