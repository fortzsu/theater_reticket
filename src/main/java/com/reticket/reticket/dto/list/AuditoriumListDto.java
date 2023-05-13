package com.reticket.reticket.dto.list;

public class AuditoriumListDto {

    private String auditoriumName;
    private String auditoriumAddress;

    public AuditoriumListDto(String auditoriumName, String auditoriumAddress) {
        this.auditoriumName = auditoriumName;
        this.auditoriumAddress = auditoriumAddress;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    public String getAuditoriumAddress() {
        return auditoriumAddress;
    }

    public void setAuditoriumAddress(String auditoriumAddress) {
        this.auditoriumAddress = auditoriumAddress;
    }
}
