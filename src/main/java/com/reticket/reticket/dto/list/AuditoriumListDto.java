package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuditoriumListDto {

    private String auditoriumName;
    private String auditoriumAddress;

    public AuditoriumListDto(String auditoriumName, AddressEntity addressEntity) {
        this.auditoriumName = auditoriumName;
        this.auditoriumAddress = addressEntity.toString();
    }

    public AuditoriumListDto(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

}
