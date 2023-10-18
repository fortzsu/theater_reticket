package com.reticket.reticket.dto.save;

import com.reticket.reticket.domain.Auditorium;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class AuditoriumSaveDto {

    private Long id;
    private String auditoriumName;
    private Integer numberOfRows;
    private Integer seatNumberPerAuditoriumRow;
    private List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList;
    private Long theatreId;

    public AuditoriumSaveDto(String auditoriumName, Long theatreId, Integer numberOfRows, Integer seatNumberPerAuditoriumRow,
                             List<AuditoriumPriceCategorySaveDto> auditoriumPriceCategorySaveDtoList) {
        this.auditoriumName = auditoriumName;
        this.numberOfRows = numberOfRows;
        this.seatNumberPerAuditoriumRow = seatNumberPerAuditoriumRow;
        this.auditoriumPriceCategorySaveDtoList = auditoriumPriceCategorySaveDtoList;
        this.theatreId = theatreId;
    }

    public AuditoriumSaveDto(Auditorium auditorium) {
    }
}
