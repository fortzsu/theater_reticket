package com.reticket.reticket.dto.save;


public class PriceSaveDto {

    private Integer amount;

    private Long playId;

    public PriceSaveDto() {
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getPlayId() {
        return playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }
}
