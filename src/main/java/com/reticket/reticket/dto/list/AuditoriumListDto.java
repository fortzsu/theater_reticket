package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.AddressEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuditoriumListDto {

    private String auditoriumName;
    private String auditoriumAddress;
    private Long auditoriumId;

    public AuditoriumListDto(String auditoriumName, AddressEntity addressEntity) {
        this.auditoriumName = auditoriumName;
        this.auditoriumAddress = addressEntity.toString();
    }

    public AuditoriumListDto(String auditoriumName, Long auditoriumId) {
        this.auditoriumName = auditoriumName;
        this.auditoriumId = auditoriumId;
    }
}
