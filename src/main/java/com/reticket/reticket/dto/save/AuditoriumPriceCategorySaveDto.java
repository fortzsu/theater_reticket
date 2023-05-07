package com.reticket.reticket.dto.save;

public class AuditoriumPriceCategorySaveDto {


    private Integer priceCategoryNumber;

    private Integer rowNumberWherePriceCategoryChanges;

    public AuditoriumPriceCategorySaveDto() {
    }

    public AuditoriumPriceCategorySaveDto(Integer priceCategoryNumber, Integer rowNumberWherePriceCategoryChanges) {
        this.priceCategoryNumber = priceCategoryNumber;
        this.rowNumberWherePriceCategoryChanges = rowNumberWherePriceCategoryChanges;
    }

    public Integer getPriceCategoryNumber() {
        return priceCategoryNumber;
    }

    public void setPriceCategoryNumber(Integer priceCategoryNumber) {
        this.priceCategoryNumber = priceCategoryNumber;
    }

    public Integer getRowNumberWherePriceCategoryChanges() {
        return rowNumberWherePriceCategoryChanges;
    }

    public void setRowNumberWherePriceCategoryChanges(Integer rowNumberWherePriceCategoryChanges) {
        this.rowNumberWherePriceCategoryChanges = rowNumberWherePriceCategoryChanges;
    }
}
