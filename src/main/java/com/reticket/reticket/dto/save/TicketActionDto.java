package com.reticket.reticket.dto.save;

import java.util.List;

public class TicketActionDto {
    private String path;
    private String username;
    private List<Long> ticketId;

    public TicketActionDto(String path, String username, List<Long> ticketId) {
        this.path = path;
        this.username = username;
        this.ticketId = ticketId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getTicketId() {
        return ticketId;
    }

    public void setTicketId(List<Long> ticketId) {
        this.ticketId = ticketId;
    }
}
