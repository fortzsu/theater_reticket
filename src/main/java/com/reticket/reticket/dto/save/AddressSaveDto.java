package com.reticket.reticket.dto.save;


public class AddressSaveDto {

    private String postCode;
    private String city;
    private String street;
    private Integer houseNumber;
    private Long auditoriumId;

    public AddressSaveDto() {
    }

    public AddressSaveDto(String postCode, String city, String street, Integer houseNumber, Long auditoriumId) {
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.auditoriumId = auditoriumId;
    }

    public Long getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Long auditoriumId) {
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
}
