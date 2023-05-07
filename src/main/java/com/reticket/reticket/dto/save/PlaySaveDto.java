package com.reticket.reticket.dto.save;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaySaveDto {

    private String playName;

    private String plot;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime premiere;

    private Long auditoriumId;

    private List<Integer> prices = new ArrayList<>();

    private String playType;

    private List<ContributorsSaveForPlaySaveDto> contributorsSaveForPlaySaveDtoList;

    public PlaySaveDto() {
    }

    public PlaySaveDto(String playName, String plot, LocalDateTime premiere, Long auditoriumId, List<Integer> prices, String playType) {
        this.playName = playName;
        this.plot = plot;
        this.premiere = premiere;
        this.auditoriumId = auditoriumId;
        this.prices = prices;
        this.playType = playType;
    }

    public String getPlayName() {
        return playName;
    }

    public LocalDateTime getPremiere() {
        return premiere;
    }

    public void setPremiere(LocalDateTime premiere) {
        this.premiere = premiere;
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

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public List<ContributorsSaveForPlaySaveDto> getContributorsSaveForPlaySaveDtoList() {
        return contributorsSaveForPlaySaveDtoList;
    }

    public void setContributorsSaveForPlaySaveDtoList(List<ContributorsSaveForPlaySaveDto> contributorsSaveForPlaySaveDtoList) {
        this.contributorsSaveForPlaySaveDtoList = contributorsSaveForPlaySaveDtoList;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }
}
