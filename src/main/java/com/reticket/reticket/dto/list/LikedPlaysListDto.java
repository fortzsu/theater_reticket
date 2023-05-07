package com.reticket.reticket.dto.list;

import java.util.List;

public class LikedPlaysListDto {

    private String playName;
    private String playPlot;
    private String theatreName;
    private String theatreAddress;
    private List<PerformanceListToLikedPlaysDto> performanceListDtoListToLikedPlays;
    private List<ListContributorsDto> listContributorsDto;

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPlayPlot() {
        return playPlot;
    }

    public void setPlayPlot(String playPlot) {
        this.playPlot = playPlot;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreAddress() {
        return theatreAddress;
    }

    public void setTheatreAddress(String theatreAddress) {
        this.theatreAddress = theatreAddress;
    }

    public List<PerformanceListToLikedPlaysDto> getPerformanceListDtoListToLikedPlays() {
        return performanceListDtoListToLikedPlays;
    }

    public void setPerformanceListDtoListToLikedPlays(List<PerformanceListToLikedPlaysDto> performanceListDtoListToLikedPlays) {
        this.performanceListDtoListToLikedPlays = performanceListDtoListToLikedPlays;
    }

    public List<ListContributorsDto> getListContributorsDto() {
        return listContributorsDto;
    }

    public void setListContributorsDto(List<ListContributorsDto> listContributorsDto) {
        this.listContributorsDto = listContributorsDto;
    }

}
