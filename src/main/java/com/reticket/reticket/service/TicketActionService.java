package com.reticket.reticket.service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.domain.Ticket;
import com.reticket.reticket.domain.TicketActionFollowerEntity;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.save.TicketActionDto;
import com.reticket.reticket.repository.TicketActionFollowerRepository;
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

    private final TicketActionFollowerRepository ticketActionFollowerRepository;

    @Autowired
    public TicketActionService(TicketService ticketService,
                               AppUserService appUserService, TicketActionFollowerRepository ticketActionFollowerRepository) {
        this.ticketService = ticketService;
        this.appUserService = appUserService;
        this.ticketActionFollowerRepository = ticketActionFollowerRepository;
    }

    public void ticketAction(List<TicketActionDto> ticketActionDtoList) {
        for (TicketActionDto ticketActionDto : ticketActionDtoList) {
            AppUser appUser = this.appUserService.findByUsername(ticketActionDto.getUsername());
            for (Long ticketId : ticketActionDto.getTicketId()) {
                Ticket ticket = this.ticketService.findByTicketId(ticketId);
                ticket.setAppUser(appUser);
                if (ticketActionDto.getPath().equals("buy")) {
                    ticket.setTicketCondition(TicketCondition.SOLD);
                } else if (ticketActionDto.getPath().equals("reserve")) {
                    ticket.setTicketCondition(TicketCondition.RESERVED);
                } else {
                    if (ticket.getTicketCondition().equals(TicketCondition.SOLD) && ticket.getAppUser().getUsername().equals(ticketActionDto.getUsername())) {
                        ticket.setTicketCondition(TicketCondition.RETURNED);
                    }
                }
                createTicketActionFollower(appUser, ticket);
            }
        }
    }

    private void createTicketActionFollower(AppUser appUser, Ticket ticket) {
        TicketActionFollowerEntity entity = new TicketActionFollowerEntity();
        entity.setTicket(ticket);
        entity.setModifiedAt(LocalDateTime.now());
        entity.setTicketCondition(ticket.getTicketCondition());
        entity.setAppUser(appUser);
        this.ticketActionFollowerRepository.save(entity);
    }
}
