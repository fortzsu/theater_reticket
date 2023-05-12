package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.enums.PlayType;

import java.time.LocalDateTime;

public class ListPlaysDto {

    private String playName;

    private String plot;

    private LocalDateTime premiere;

    private PlayType playType;

    public ListPlaysDto(Play play) {
        this.playName = play.getPlayName();
        this.plot = play.getPlot();
        this.premiere = play.getPremiere();
        this.playType = play.getPlayType();
    }

    public ListPlaysDto() {
    }

    public String getPlayName() {
        return playName;
    }

    public String getPlot() {
        return plot;
    }

    public LocalDateTime getPremiere() {
        return premiere;
    }

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPremiere(LocalDateTime premiere) {
        this.premiere = premiere;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
    }
}
