package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.enums.PlayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListPlaysDto {

    private String playName;

    private String plot;

    private LocalDateTime premiere;

    private PlayType playType;

//    public ListPlaysDto(Play play) {
//        this.playName = play.getPlayName();
//        this.plot = play.getPlot();
//        this.premiere = play.getPremiere();
//        this.playType = play.getPlayType();
//    }


    public ListPlaysDto(String playName, String plot, LocalDateTime premiere, PlayType playType) {
        this.playName = playName;
        this.plot = plot;
        this.premiere = premiere;
        this.playType = playType;
    }
}
