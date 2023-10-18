package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LikedPlaysListDto {

    private String playName;
    private String playPlot;
    private String theatreName;
    private String theatreAddress;
    private List<PerformanceListToLikedPlaysDto> performanceListDtoListToLikedPlays;
    private List<ListContributorsDto> listContributorsDto;


}
