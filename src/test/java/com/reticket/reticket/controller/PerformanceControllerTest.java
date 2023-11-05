package com.reticket.reticket.controller;

import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.PerformanceSaveDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PerformanceControllerTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void testSavePerformance_withSuper_404() {
        PerformanceSaveDto dto = new PerformanceSaveDto(LocalDateTime.now(), 100L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PerformanceSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/performance", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSavePerformance_withTheaterUser_403() {
        PerformanceSaveDto dto = new PerformanceSaveDto(LocalDateTime.now(), 100L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PerformanceSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/performance", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSavePerformance_withWrongUser_401() {
        PerformanceSaveDto dto = new PerformanceSaveDto(LocalDateTime.now(), 100L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PerformanceSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/performance", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdatePerformance_withSuper_404() {
        UpdatePerformanceDto dto = new UpdatePerformanceDto(LocalDateTime.now(), false, false, false);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePerformanceDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/performance/updatePerformance/1000", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdatePerformance_withTheaterUser_403() {
        UpdatePerformanceDto dto = new UpdatePerformanceDto(LocalDateTime.now(), false, false, false);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePerformanceDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/performance/updatePerformance/1000", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdatePerformance_withWrongUser_401() {
        UpdatePerformanceDto dto = new UpdatePerformanceDto(LocalDateTime.now(), false, false, false);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePerformanceDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/performance/updatePerformance/1000", HttpMethod.POST, request, String.class);
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
