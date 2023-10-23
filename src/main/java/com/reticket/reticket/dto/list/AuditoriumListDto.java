package com.reticket.reticket.dto.list;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.dto.wrapper.WrapperDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuditoriumListDto extends WrapperDto {

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
