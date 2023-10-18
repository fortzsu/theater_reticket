package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuditoriumPriceCategorySaveDto {


    private Integer priceCategoryNumber;

    private Integer rowNumberWherePriceCategoryChanges;

    public AuditoriumPriceCategorySaveDto() {
    }

    public AuditoriumPriceCategorySaveDto(Integer priceCategoryNumber, Integer rowNumberWherePriceCategoryChanges) {
        this.priceCategoryNumber = priceCategoryNumber;
        this.rowNumberWherePriceCategoryChanges = rowNumberWherePriceCategoryChanges;
    }

}
