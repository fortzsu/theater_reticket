package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.TicketCondition;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_action_follower_entity")
public class TicketActionFollowerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_condition")
    private TicketCondition ticketCondition;
    @OneToOne
    private AppUser appUser;
    @ManyToOne
    private Ticket ticket;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
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

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
