package com.reticket.reticket.controller;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.AssociateUserSaveDto;
import com.reticket.reticket.dto.save.GuestUserSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
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
public class AppUserControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testUpdateAppUser_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_2", "password",
                "username_2@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("username_21@testemail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/username_2", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withGuest_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_3", "password",
                "username_3@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("username_31@testemail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_3@testemail.com", "password")
                .exchange("/api/appUser/username_3", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withWrongUser_401() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_4", "password",
                "username_4@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("username_41@testemail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("wrong", "password")
                .exchange("/api/appUser/username_4", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withWithGuest_NOT_FOUND() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_5", "password",
                "username_5@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("username_51@testemail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_5@testemail.com", "password")
                .exchange("/api/appUser/username_1000", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, updateResult.getStatusCode());
    }


    @Test
    public void testDeleteAppUser_withGuest_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_6", "password",
                 "username_6@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        HttpEntity<Boolean> updateRequest = new HttpEntity<>(updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_6@testemail.com", "password")
                .exchange("/api/appUser/username_6", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testDeleteAppUser_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_7", "password",
                 "username_7@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        HttpEntity<Boolean> updateRequest = new HttpEntity<>(updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/username_7", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }


        @Test
    public void testSaveGuest_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_1", "password",
                 "username_1@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @Test
    public void testSaveGuest_withNoUser_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username1",
                "password", "username1@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
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

    @Test
    public void testAppUserListTickets_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_8", "password",
                 "username_8@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<ListTicketDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/username_8/tickets", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListTickets_withGuest_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_9", "password",
                 "username_9@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<ListTicketDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_9@testemail.com", "password")
                .exchange("/api/appUser/username_9/tickets", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListTickets_withWrongUser_401() {
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<ListTicketDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("wrong", "password")
                .exchange("/api/appUser/username_9/tickets", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListLikedPlays_withSuper_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_10", "password",
                 "username_10@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/username_10/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListLikedPlays_withGuest_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_11", "password",
                 "username_11@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_11@testemail.com", "password")
                .exchange("/api/appUser/username_11/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListLikedPlays_withWrongUser_401() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_11", "password",
                "username_11@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("wrong", "password")
                .exchange("/api/appUser/username_11/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, listResult.getStatusCode());
    }

    @Test
    public void testAppUserLikePlay_withSuper_403() {
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<Void> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/username_12/1", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.FORBIDDEN, listResult.getStatusCode());
    }

        @Test
    public void testSaveAssociate_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        GuestUserSaveDto guestUserSaveDto = new GuestUserSaveDto("AssociateFirst", "AssociateLast", "associate_1",
                "password", "associate_2@testemail.com");
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(guestUserSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/appUser/saveAssociate/false", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testSaveAssociate_withSuper_200() {
        HttpHeaders headers = new HttpHeaders();
        GuestUserSaveDto guestUserSaveDto = new GuestUserSaveDto("AssociateFirst", "AssociateLast", "associate_1",
                "password",  "associate_1@testemail.com");
        AssociateUserSaveDto associateUserSaveDto = new AssociateUserSaveDto(false, 1L, guestUserSaveDto);
        HttpEntity<AssociateUserSaveDto> request = new HttpEntity<>(associateUserSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/appUser/saveAssociate", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSaveAssociate_withGuestUser_403() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_14",
                "password","username_14@testemail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        HttpHeaders headers = new HttpHeaders();
        GuestUserSaveDto guestUserSaveDto = new GuestUserSaveDto("AssociateFirst", "AssociateLast",
                "associate_1","password", "associate_3@testemail.com");
        AssociateUserSaveDto associateUserSaveDto = new AssociateUserSaveDto(false, 1L, guestUserSaveDto);
        HttpEntity<AssociateUserSaveDto> request = new HttpEntity<>(associateUserSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_14@testemail.com", "password")
                .exchange("/api/appUser/saveAssociate", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

}
