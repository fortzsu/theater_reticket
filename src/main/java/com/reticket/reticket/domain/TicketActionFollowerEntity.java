package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.TicketCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_action_follower_entity")
@Getter
@Setter
@NoArgsConstructor
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


}
