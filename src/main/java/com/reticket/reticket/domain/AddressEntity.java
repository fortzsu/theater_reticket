package com.reticket.reticket.domain;

import jakarta.persistence.*;


@Entity
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

    public AddressEntity() {
    }

    public Auditorium getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Auditorium auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return postCode + ", " + city + " " + street + " " + houseNumber;
    }
}
