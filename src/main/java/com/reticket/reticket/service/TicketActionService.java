package com.reticket.reticket.service;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.domain.Ticket;
import com.reticket.reticket.domain.TicketActionFollowerEntity;
import com.reticket.reticket.domain.enums.TicketCondition;
import com.reticket.reticket.dto.save.TicketActionDto;
import com.reticket.reticket.repository.TicketActionFollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketActionService {

    private final TicketService ticketService;
    private final AppUserService appUserService;
    private final TicketActionFollowerRepository ticketActionFollowerRepository;

    public void ticketAction(TicketActionDto ticketActionDto) {
        AppUser appUser = this.appUserService.findByUsername(ticketActionDto.getUsername());
        addTicketsToAppUser(appUser, ticketActionDto);
    }

    private void addTicketsToAppUser(AppUser appUser, TicketActionDto ticketActionDto) {
        if (appUser != null) {
            for (Long ticketId : ticketActionDto.getTicketId()) {
                Ticket ticket = this.ticketService.findByTicketId(ticketId);
                ticket.setAppUser(appUser);
                searchTicketActionPath(ticketActionDto, ticket);
                createTicketActionFollower(appUser, ticket);
            }
        }
    }

    private void searchTicketActionPath(TicketActionDto ticketActionDto, Ticket ticket) {
        String appUserName = ticket.getAppUser().getUsername();
        String currentUsername = ticketActionDto.getUsername();
        if (ticketActionDto.getPath().equals("buy")) {
            ticket.setTicketCondition(TicketCondition.SOLD);
        } else if (ticketActionDto.getPath().equals("reserve")) {
            ticket.setTicketCondition(TicketCondition.RESERVED);
        } else {
            if (ticket.getTicketCondition().equals(TicketCondition.SOLD) &&
                    appUserName.equals(currentUsername)) {
                ticket.setTicketCondition(TicketCondition.RETURNED);
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
