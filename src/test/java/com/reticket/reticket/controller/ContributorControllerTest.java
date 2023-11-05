package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.ContributorSaveDto;
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
public class ContributorControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testListContributor_withSuper_200() {
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .getForEntity("/api/contributor", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListContributor_withNoUser_200() {
        ResponseEntity<String> result = template.getForEntity("/api/contributor", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListContributor_withWrongUser_401() {
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .getForEntity("/api/contributor", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
    @Test
    public void testSaveContributor_withSuper_200() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withTheaterAdmin_200() {
        ContributorSaveDto dto = new ContributorSaveDto("First",
                "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withTheaterAdmin_403() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withWrongUser_401() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withSuper_404() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/contributor/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withTheaterUser_403() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/contributor/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withWrongUser_401() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/contributor/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
