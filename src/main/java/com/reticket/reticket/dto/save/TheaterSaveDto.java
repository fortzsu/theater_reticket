package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TheaterSaveDto {

    private Long id;
    private String theatreName;
    private String theatreStory;


    public TheaterSaveDto(String theatreName, String theatreStory) {
        this.theatreName = theatreName;
        this.theatreStory = theatreStory;
    }

}
