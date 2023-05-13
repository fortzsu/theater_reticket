package com.reticket.reticket.dto.save;


public class TheaterSaveDto {

    private Long id;
    private String theatreName;

    private String theatreStory;

    public TheaterSaveDto() {
    }

    public TheaterSaveDto(String theatreName, String theatreStory) {
        this.theatreName = theatreName;
        this.theatreStory = theatreStory;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }


    public String getTheatreStory() {
        return theatreStory;
    }

    public void setTheatreStory(String theatreStory) {
        this.theatreStory = theatreStory;
    }
}
