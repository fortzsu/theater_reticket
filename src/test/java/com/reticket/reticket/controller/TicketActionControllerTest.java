package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.TicketActionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketActionControllerTest {

    @Autowired
    private TestRestTemplate template;

        @Test
    public void testTicketAction_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> listRequest = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testTicketAction_withSuperUser_200() {
        HttpHeaders headers = new HttpHeaders();
        TicketActionDto dto = new TicketActionDto("buy", "guestOne", List.of(1L,2L));
        HttpEntity<TicketActionDto> listRequest = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testTicketAction_withTheaterUser_200() {
        HttpHeaders headers = new HttpHeaders();
        TicketActionDto dto = new TicketActionDto("buy", "guestTwo", List.of(1L,2L));
        HttpEntity<TicketActionDto> listRequest = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }




}
