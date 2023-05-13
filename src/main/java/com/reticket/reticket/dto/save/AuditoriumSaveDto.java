package com.reticket.reticket.dto.save;

import com.reticket.reticket.domain.Auditorium;

import java.util.List;

public class AuditoriumSaveDto {

    private Long id;
    private String auditoriumName;
    private Integer numberOfRows;
    private Integer seatNumberPerAuditoriumRow;
    private List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList;
    private Long theatreId;

    public AuditoriumSaveDto() {
    }

    public AuditoriumSaveDto(String auditoriumName, Long theatreId, Integer numberOfRows, Integer seatNumberPerAuditoriumRow,
                             List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList) {
        this.auditoriumName = auditoriumName;
        this.numberOfRows = numberOfRows;
        this.seatNumberPerAuditoriumRow = seatNumberPerAuditoriumRow;
        this.auditoriumPriceCategorySaveDtoList = auditoriumPriceCategorySaveDtoList;
        this.theatreId = theatreId;
    }

    public AuditoriumSaveDto(Auditorium auditorium) {
        this.id = auditorium.getId();
        this.auditoriumName = auditorium.getAuditoriumName();
        this.theatreId = auditorium.getTheater().getId();
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getSeatNumberPerAuditoriumRow() {
        return seatNumberPerAuditoriumRow;
    }

    public void setSeatNumberPerAuditoriumRow(Integer seatNumberPerAuditoriumRow) {
        this.seatNumberPerAuditoriumRow = seatNumberPerAuditoriumRow;
    }


    public List<AuditoriumPriceCategorySaveDto> getAuditoriumPriceCategorySaveDtoList() {
        return auditoriumPriceCategorySaveDtoList;
    }

    public void setAuditoriumPriceCategorySaveDtoList(List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList) {
        this.auditoriumPriceCategorySaveDtoList = auditoriumPriceCategorySaveDtoList;
    }
}
