package com.reticket.reticket.controller;


import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
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
public class ListAndSearchControllerTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void testListAuditorium_withSuper_200() {
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .getForEntity("/api/auditorium/list", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListAuditorium_withNoUser_200() {
        ResponseEntity<String> result = template.getForEntity("/api/auditorium/list", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListAuditorium_withWrongUser_401() {
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .getForEntity("/api/auditorium/list", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testListContributor_withSuper_200() {
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .getForEntity("/api/contributor/list", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListContributor_withNoUser_200() {
        ResponseEntity<String> result = template.getForEntity("/api/contributor/list", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListContributor_withWrongUser_401() {
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .getForEntity("/api/contributor/list", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testListPlay_withSuper_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/play/list", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListPlay_withNoUser_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/play/list", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListPlay_withWrongUser_401() {
        PageableDto pageableDto = new PageableDto(0,5);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PageableDto> request = new HttpEntity<>(pageableDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/play/list", request, String.class);
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

    @Test
    public void testPerformanceSearch_withSuper_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        SearchDateDto dateDto = new SearchDateDto(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 1,
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 10);
        FilterPerformancesDto dto = new FilterPerformancesDto("theater", 1L, dateDto, pageableDto);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FilterPerformancesDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/performance/search", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testPerformanceSearch_withNoUser_200() {
        PageableDto pageableDto = new PageableDto(0,5);
        SearchDateDto dateDto = new SearchDateDto(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 1,
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 10);
        FilterPerformancesDto dto = new FilterPerformancesDto("theater", 1L, dateDto, pageableDto);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FilterPerformancesDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/performance/search", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testPerformanceSearch_withWrongUser_401() {
        PageableDto pageableDto = new PageableDto(0,5);
        SearchDateDto dateDto = new SearchDateDto(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 1,
                LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(), 10);
        FilterPerformancesDto dto = new FilterPerformancesDto("theater", 1L, dateDto, pageableDto);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FilterPerformancesDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/performance/search", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }


}
