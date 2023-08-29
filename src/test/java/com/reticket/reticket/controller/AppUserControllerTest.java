package com.reticket.reticket.controller;

import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testSaveGuest_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_1", "password",
                 "username_1@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testSaveGuest_withNoUser_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username1",
                "password", "username1@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSaveGuest_withWrongUser_401() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username2",
                "password", "username2@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
