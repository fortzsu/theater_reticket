package com.reticket.reticket.dto.save;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class TicketActionDto {
    private String path;
    private String username;
    private List<Long> ticketId;

    public TicketActionDto(String path, String username, List<Long> ticketId) {
        this.path = path;
        this.username = username;
        this.ticketId = ticketId;
    }

}
