package com.reticket.reticket.controller;


import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TheaterControllerTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void testCreateTheater_withSuper_200() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/theater", request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testCreateTheater_withWrongUser_401() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/theater", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateTheater_withSuper_authOk_404() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<Boolean> result = template.withBasicAuth("reticket23@gmail.com", "test")
                        .exchange("/api/theater/theaterName", HttpMethod.PUT, request, Boolean.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateTheater_withWrongUser_401() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/theater/theaterName", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testDeleteTheater_withSuper_authOk_404() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/theater/theaterName", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteTheater_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/theater/theaterName", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }


    @Test
    public void testListTheater_withSuper_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/theater/list", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListTheater_withNoUser_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/theater/list", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListTheater_withWrongUser_401() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/theater/list", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
