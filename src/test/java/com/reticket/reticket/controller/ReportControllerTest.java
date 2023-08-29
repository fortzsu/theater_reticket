package com.reticket.reticket.controller;


import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
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
public class ReportControllerTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void testReport_withGuestUser_403() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_13", "password",
                "username_13@testemail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_13@testemail.com", "password")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testReport_withSuperUser_200() {
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testReport_withTheaterAdmin_200() {
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testReport_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
