package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.TicketCondition;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
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
    @ManyToOne
    private AppUser appUser;
    @OneToMany(mappedBy = "ticket")
    private List<TicketActionFollowerEntity> ticketActionFollowerEntities;

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


    public List<TicketActionFollowerEntity> getTicketActionFollowerEntities() {
        return ticketActionFollowerEntities;
    }

    public void setTicketActionFollowerEntities(List<TicketActionFollowerEntity> ticketActionFollowerEntities) {
        this.ticketActionFollowerEntities = ticketActionFollowerEntities;
    }
}
