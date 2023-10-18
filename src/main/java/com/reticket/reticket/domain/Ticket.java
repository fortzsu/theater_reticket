package com.reticket.reticket.domain;

import com.reticket.reticket.domain.enums.TicketCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
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

}
