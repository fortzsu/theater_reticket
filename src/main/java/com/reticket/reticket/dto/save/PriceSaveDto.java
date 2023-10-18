package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PriceSaveDto {

    private Integer amount;

    private Long playId;

    public PriceSaveDto() {
    }


}
