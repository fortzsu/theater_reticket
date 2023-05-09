package com.reticket.reticket.service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.domain.Ticket;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.save.TicketActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketActionService {

    private final TicketService ticketService;

    private final AppUserService appUserService;

    @Autowired
    public TicketActionService(TicketService ticketService,
                               AppUserService appUserService) {
        this.ticketService = ticketService;
        this.appUserService = appUserService;
    }

    public boolean ticketAction(List<TicketActionDto> ticketActionDtoList) {
        boolean flag = true;
        for (TicketActionDto ticketActionDto : ticketActionDtoList) {
            AppUser appUser = this.appUserService.findByUsername(ticketActionDto.getUsername());
            for (Long ticketId : ticketActionDto.getTicketId()) {
                Ticket ticket = this.ticketService.findByTicketId(ticketId);
                ticket.setAppUser(appUser);
                if (ticketActionDto.getPath().equals("buy")) {
                    ticket.setTicketCondition(TicketCondition.SOLD);
                    ticket.setSoldAt(LocalDateTime.now());
                } else if (ticketActionDto.getPath().equals("reserve")) {
                    ticket.setTicketCondition(TicketCondition.RESERVED);
                    ticket.setReservedAt(LocalDateTime.now());
                } else {
                    if (ticket.getTicketCondition().equals(TicketCondition.SOLD) && ticket.getAppUser().getUsername().equals(ticketActionDto.getUsername())) {
                        ticket.setTicketCondition(TicketCondition.RETURNED);
                        ticket.setReturnedAt(LocalDateTime.now());
                    } else {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }
}
