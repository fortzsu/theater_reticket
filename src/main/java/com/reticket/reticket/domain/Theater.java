package com.reticket.reticket.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theater")
@Getter
@Setter
@NoArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "theater_name")
    private String theaterName;

    @Column(name = "theater_story")
    private String theaterStory;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "theater", fetch = FetchType.EAGER)
    private List<Auditorium> auditoriums = new ArrayList<>();

    @Column(name = "is_archived")
    private Boolean isArchived;

}

