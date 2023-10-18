package com.reticket.reticket.dto.save;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class PlaySaveDto {

    private String playName;

    private String plot;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime premiere;

    private Long auditoriumId;

    private List<Integer> prices = new ArrayList<>();

    private String playType;

    private List<ContributorsSaveForPlaySaveDto> contributorsSaveForPlaySaveDtoList;


    public PlaySaveDto(String playName, String plot, LocalDateTime premiere, Long auditoriumId, List<Integer> prices, String playType) {
        this.playName = playName;
        this.plot = plot;
        this.premiere = premiere;
        this.auditoriumId = auditoriumId;
        this.prices = prices;
        this.playType = playType;
    }


}
