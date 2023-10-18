package com.reticket.reticket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contributor")
@Getter
@Setter
@NoArgsConstructor
public class Contributor {
    @Id
    @Column(name = "contributor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "introduction")
    private String introduction;

}
