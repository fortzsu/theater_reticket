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
    public void testTicketAction_withSuperUser_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        List<TicketActionDto> dtoList = List.of(new TicketActionDto("buy", "user", List.of(1L,2L)));
        HttpEntity<List<TicketActionDto>> listRequest = new HttpEntity<>(dtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testTicketAction_withTheaterUser_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        List<TicketActionDto> dtoList = List.of(new TicketActionDto("buy", "user", List.of(1L,2L)));
        HttpEntity<List<TicketActionDto>> listRequest = new HttpEntity<>(dtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }




}
