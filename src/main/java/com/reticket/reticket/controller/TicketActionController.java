package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.TicketActionDto;
import com.reticket.reticket.service.TicketActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/ticketAction")
@RequiredArgsConstructor
public class TicketActionController {

    private final TicketActionService ticketActionService;

    @PostMapping
    @PreAuthorize("hasAuthority('TICKET_ACTIONS')")
    public ResponseEntity<Void> ticketAction(@RequestBody List<TicketActionDto> ticketActionDtoList) {
        this.ticketActionService.ticketAction(ticketActionDtoList);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
