package com.reticket.reticket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter @Setter @NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    @OneToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditoriumId;
    //TODO @OneToMany
    @Override
    public String toString() {
        return postCode + ", " + city + " " + street + " " + houseNumber;
    }
}
