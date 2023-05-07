package com.reticket.reticket.dto.save;


public class TheatreSaveDto {

    private Long id;
    private String theatreName;

    public TheatreSaveDto() {
    }

    public TheatreSaveDto(String theatreName) {
        this.theatreName = theatreName;
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



}
