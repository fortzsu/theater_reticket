package com.reticket.reticket.dto.list;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListTicketDto {

    private String theatreName;
    private String auditoriumAddress;
    private String playName;
    private String performanceDateTime;
    private String ticketCondition;
    private Integer ticketPrice;

}
