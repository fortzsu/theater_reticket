package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListTheatersDto {

    private String theaterName;
    private String theaterStory;
    private List<AuditoriumListDto> auditoriumListDtoList = new ArrayList<>();

    public ListTheatersDto(String theaterName, String theaterStory) {
        this.theaterName = theaterName;
        this.theaterStory = theaterStory;
    }


    public void addAuditoriumListDtoList(AuditoriumListDto auditoriumListDtoList) {
        this.auditoriumListDtoList.add(auditoriumListDtoList);
    }


}
