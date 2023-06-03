package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.TicketActionDto;
import com.reticket.reticket.service.TicketActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/ticketAction")
public class TicketActionController {

    private final TicketActionService ticketActionService;

    @Autowired
    public TicketActionController(TicketActionService ticketActionService) {
        this.ticketActionService = ticketActionService;
    }

    @PostMapping()
    public ResponseEntity<Void> ticketAction(@RequestBody List<TicketActionDto> ticketActionDtoList) {
        if(this.ticketActionService.ticketAction(ticketActionDtoList)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }







}
