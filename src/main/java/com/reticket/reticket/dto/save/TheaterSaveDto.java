package com.reticket.reticket.dto.save;


public class TheaterSaveDto {

    private Long id;
    private String theatreName;

    private String theatreHistory;

    public TheaterSaveDto() {
    }

    public TheaterSaveDto(String theatreName, String theatreHistory) {
        this.theatreName = theatreName;
        this.theatreHistory = theatreHistory;
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


    public String getTheatreHistory() {
        return theatreHistory;
    }

    public void setTheatreHistory(String theatreHistory) {
        this.theatreHistory = theatreHistory;
    }
}
