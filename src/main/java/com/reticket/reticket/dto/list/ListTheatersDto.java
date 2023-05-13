package com.reticket.reticket.dto.list;

import java.util.ArrayList;
import java.util.List;

public class ListTheatersDto {

    private String theaterName;
    private String theaterStory;
    private List<AuditoriumListDto> auditoriumListDtoList = new ArrayList<>();

    public ListTheatersDto(String theaterName, String theaterStory) {
        this.theaterName = theaterName;
        this.theaterStory = theaterStory;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getTheaterStory() {
        return theaterStory;
    }

    public void setTheaterStory(String theaterStory) {
        this.theaterStory = theaterStory;
    }

    public List<AuditoriumListDto> getAuditoriumListDtoList() {
        return auditoriumListDtoList;
    }

    public void addAuditoriumListDtoList(AuditoriumListDto auditoriumListDtoList) {
        this.auditoriumListDtoList.add(auditoriumListDtoList);
    }


}
