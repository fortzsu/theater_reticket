package com.reticket.reticket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "performance")
@Getter
@Setter
@NoArgsConstructor
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

}
