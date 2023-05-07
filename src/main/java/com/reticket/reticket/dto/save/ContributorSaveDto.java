package com.reticket.reticket.dto.save;

public class ContributorSaveDto {

    private String firstName;
    private String lastName;
    private String introduction;

    public ContributorSaveDto() {
    }

    public ContributorSaveDto(String firstName, String lastName, String introduction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.introduction = introduction;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
