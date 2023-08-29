package com.reticket.reticket.controller;

import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.save.ContributorsSaveForPlaySaveDto;
import com.reticket.reticket.dto.save.PlaySaveDto;
import com.reticket.reticket.dto.update.UpdatePlayDto;
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
public class PlayControllerTest {

    @Autowired
    private TestRestTemplate template;

//    @Test
//    public void testSavePlay_withSuper_NOT_FOUND() {
//        PlaySaveDto playSaveDto = new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1, 2, 3), "drama");
//        playSaveDto.setContributorsSaveForPlaySaveDtoList(List.of(new ContributorsSaveForPlaySaveDto()));
//        List<PlaySaveDto> playSaveDtoList = List.of(playSaveDto);
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<List<PlaySaveDto>> request = new HttpEntity<>(playSaveDtoList, headers);
//        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
//                .exchange("/api/play/save", HttpMethod.POST, request, String.class);
//        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
//    }

    @Test
    public void testSavePlay_withTheaterUser_403() {
        List<PlaySaveDto> playSaveDtoList = List.of(new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1, 2, 3), "drama"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PlaySaveDto>> request = new HttpEntity<>(playSaveDtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/play/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSavePlay_withWrongUser_401() {
        List<PlaySaveDto> playSaveDtoList = List.of(new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1, 2, 3), "drama"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PlaySaveDto>> request = new HttpEntity<>(playSaveDtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/play/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testGetFormDataPlay_withSuper_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/play/formData/1000", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testGetFormDataPlay_withTheaterUser_403() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/play/formData/1000", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testGetFormDataPlay_withWrongUser_403() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/play/formData/1000", HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdatePlay_withTheaterAdmin_NOT_FOUND() {
        UpdatePlayDto updatePlayDto = new UpdatePlayDto("PlayName", "Plot", 1L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePlayDto> request = new HttpEntity<>(updatePlayDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/play/updatePlay/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdatePlay_withTheaterUser_403() {
        UpdatePlayDto updatePlayDto = new UpdatePlayDto("PlayName", "Plot", 1L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePlayDto> request = new HttpEntity<>(updatePlayDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/play/updatePlay/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdatePlay_withWrongUser_401() {
        UpdatePlayDto updatePlayDto = new UpdatePlayDto("PlayName", "Plot", 1L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UpdatePlayDto> request = new HttpEntity<>(updatePlayDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/play/updatePlay/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testDeletePlay_withTheaterAdmin_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/play/1000", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeletePlay_withTheaterUser_403() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/play/1000", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testDeletePlay_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/play/1000", HttpMethod.DELETE, request, String.class);
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
}
