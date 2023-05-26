package com.reticket.reticket.dto.update;

public class UpdatePlayDto {

    private String playName;
    private String plot;
    private Long auditoriumId;

    public UpdatePlayDto(String playName, String plot, Long auditoriumId) {
        this.playName = playName;
        this.plot = plot;
        this.auditoriumId = auditoriumId;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Long getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(Long auditoriumId) {
        this.auditoriumId = auditoriumId;
    }
}
