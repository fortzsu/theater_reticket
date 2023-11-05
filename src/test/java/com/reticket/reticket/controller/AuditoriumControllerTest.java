package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.save.AuditoriumPriceCategorySaveDto;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
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
public class AuditoriumControllerTest {

    @Autowired
    private TestRestTemplate template;


    @Test
    public void testSaveAuditorium_withSuper_200() {
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto(
                "Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/auditorium", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testSaveAuditorium_withWrongUser_401() {
        List<AuditoriumSaveDto> saveDtos = List.of(new AuditoriumSaveDto(
                "Name",1L,100,10,List.of(new AuditoriumPriceCategorySaveDto())));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<AuditoriumSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/auditorium", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testSaveAuditorium_withGuestUser_403() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_15", "password",
                 "username_15@testemail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto(
                "Name",1L,100,10,List.of(new AuditoriumPriceCategorySaveDto()));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_15@testemail.com", "password")
                .exchange("/api/auditorium", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withSuper_404() {
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto("Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/auditorium/100", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withTheaterAdmin_404() {
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto("Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/auditorium/100", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withWrongUser_401() {
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto("Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/auditorium/100", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withGuestUser_403() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_16", "password",
                 "username_16@testemail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto("Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_16@testemail.com", "password")
                .exchange("/api/auditorium/100", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }


    @Test
    public void testDeleteAuditorium_withSuper_404() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/auditorium/100", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteAuditorium_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/auditorium/100", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testDeleteAuditorium_withGuestUser_403() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_17", "password",
                "username_17@testemail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("username_17@testemail.com", "password")
                .exchange("/api/auditorium/100", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testListAuditorium_withSuper_200() {
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .getForEntity("/api/auditorium", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testListAuditorium_withNoUser_200() {
        ResponseEntity<String> result = template.getForEntity("/api/auditorium", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testListAuditorium_withWrongUser_401() {
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .getForEntity("/api/auditorium", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
