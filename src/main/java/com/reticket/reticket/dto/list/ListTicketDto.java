package com.reticket.reticket.dto.list;

import com.reticket.reticket.dto.wrapper.WrapperClass;

public class ListTicketDto {

    private String theatreName;
    private String auditoriumAddress;
    private String playName;
    private String performanceDateTime;
    private String ticketCondition;
    private Integer ticketPrice;

    public ListTicketDto() {
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getAuditoriumAddress() {
        return auditoriumAddress;
    }

    public void setAuditoriumAddress(String auditoriumAddress) {
        this.auditoriumAddress = auditoriumAddress;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getPerformanceDateTime() {
        return performanceDateTime;
    }

    public void setPerformanceDateTime(String performanceDateTime) {
        this.performanceDateTime = performanceDateTime;
    }

    public String getTicketCondition() {
        return ticketCondition;
    }

    public void setTicketCondition(String ticketCondition) {
        this.ticketCondition = ticketCondition;
    }

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
