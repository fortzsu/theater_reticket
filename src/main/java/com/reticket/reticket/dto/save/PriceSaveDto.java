package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PriceSaveDto {

    private Integer amount;

    private Long playId;

}
