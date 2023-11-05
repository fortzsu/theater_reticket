package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.TicketActionDto;
import com.reticket.reticket.exception.AppUserNotFoundException;
import com.reticket.reticket.exception.TicketNotFoundException;
import com.reticket.reticket.service.TicketActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/ticketAction")
@RequiredArgsConstructor
public class TicketActionController {

    private final TicketActionService ticketActionService;

    @PostMapping
    @PreAuthorize("hasAuthority('TICKET_ACTIONS')")
    public ResponseEntity<Void> ticketAction(@RequestBody TicketActionDto ticketActionDto) {
        try {
            this.ticketActionService.ticketAction(ticketActionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppUserNotFoundException | TicketNotFoundException e) {
            Logger.getAnonymousLogger().info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
