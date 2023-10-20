package com.reticket.reticket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "auditorium")
@Getter
@Setter
@NoArgsConstructor
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
    @JoinColumn(name = "theater_id")
    private Theater theater;
    @Column
    private Boolean isActive;
    @Column
    private Integer numberOfPriceCategories;
    @Column
    @OneToMany
    private List<Seat> seats;
    @OneToOne
    private AddressEntity addressEntity;


}
