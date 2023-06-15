package com.reticket.reticket;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.*;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import com.reticket.reticket.dto.update.UpdatePerformanceDto;
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
public class ReticketControllerTest {


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


    @Test
    public void testCreateTheater_withSuper_200() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/theater/create", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testCreateTheater_withWrongUser_401() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/theater/create", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateTheater_withSuper_authOk_NOT_FOUND() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<Boolean> result = template.withBasicAuth("reticket23@gmail.com", "test")
                        .exchange("/api/theater/update/theaterName", HttpMethod.PUT, request, Boolean.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateTheater_withWrongUser_401() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/theater/update/theaterName", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testDeleteTheater_withSuper_authOk_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/theater/delete/theaterName", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteTheater_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/theater/delete/theaterName", HttpMethod.DELETE, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

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
                .exchange("/api/appUser/update/username_2", HttpMethod.PUT, updateRequest, String.class);
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
                .exchange("/api/appUser/update/username_3", HttpMethod.PUT, updateRequest, String.class);
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
                .exchange("/api/appUser/update/username_4", HttpMethod.PUT, updateRequest, String.class);
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
                .exchange("/api/appUser/update/username_1000", HttpMethod.PUT, updateRequest, String.class);
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
                .exchange("/api/appUser/delete/username_6", HttpMethod.DELETE, updateRequest, String.class);
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
                .exchange("/api/appUser/delete/username_7", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
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
    public void testAppUserLikePlay_withGuest_200() {
        GuestUserSaveDto dto = new GuestUserSaveDto("First", "Last", "username_12", "password",
                 "username_12@testemail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<GuestUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("reticket23@gmail.com", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<Void> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_12@testemail.com", "password")
                .exchange("/api/appUser/username_12/1", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, listResult.getStatusCode());
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

    @Test
    public void testAddress_withSuper_200() {
        HttpHeaders headers = new HttpHeaders();
        List<AddressSaveDto> saveDtos = List.of(new AddressSaveDto("1000", "City", "Street", 123, 1L));
        HttpEntity<List<AddressSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/address", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testAddress_withTheaterUser_403() {
        HttpHeaders headers = new HttpHeaders();
        List<AddressSaveDto> saveDtos = List.of(new AddressSaveDto("1000", "City", "Street", 123, 1L));
        HttpEntity<List<AddressSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/address", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testAddress_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        List<AddressSaveDto> saveDtos = List.of(new AddressSaveDto("1000", "City", "Street", 123, 1L));
        HttpEntity<List<AddressSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/address", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateAddress_withSuper_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        AddressSaveDto saveDto = new AddressSaveDto("1000", "City", "Street", 123, 1L);
        HttpEntity<AddressSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/address/update/2", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateAddress_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        AddressSaveDto saveDto = new AddressSaveDto();
        HttpEntity<AddressSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/address/update/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
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

    @Test
    public void testSaveAuditorium_withSuper_200() {
        List<AuditoriumSaveDto> saveDtos = List.of(new AuditoriumSaveDto(
                "Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4))));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<AuditoriumSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/auditorium", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
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
        List<AuditoriumSaveDto> saveDtos = List.of(new AuditoriumSaveDto(
                "Name",1L,100,10,List.of(new AuditoriumPriceCategorySaveDto())));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<AuditoriumSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_15@testemail.com", "password")
                .exchange("/api/auditorium", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withSuper_NOT_FOUND() {
        AuditoriumSaveDto saveDto = new AuditoriumSaveDto("Name",1L,100,10,
                List.of(new AuditoriumPriceCategorySaveDto(3,4)));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuditoriumSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/auditorium/100", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateAuditorium_withTheaterAdmin_NOT_FOUND() {
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
    public void testDeleteAuditorium_withSuper_NOT_FOUND() {
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
    public void testSaveContributor_withSuper_200() {
        List<ContributorSaveDto> listDtos = List.of(new ContributorSaveDto("First", "Last", "Introduction"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<ContributorSaveDto>> request = new HttpEntity<>(listDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withTheaterAdmin_200() {
        List<ContributorSaveDto> listDtos = List.of(new ContributorSaveDto("First", "Last", "Introduction"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<ContributorSaveDto>> request = new HttpEntity<>(listDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin@testemail.te", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withTheaterAdmin_403() {
        List<ContributorSaveDto> listDtos = List.of(new ContributorSaveDto("First", "Last", "Introduction"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<ContributorSaveDto>> request = new HttpEntity<>(listDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSaveContributor_withWrongUser_401() {
        List<ContributorSaveDto> listDtos = List.of(new ContributorSaveDto("First", "Last", "Introduction"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<ContributorSaveDto>> request = new HttpEntity<>(listDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/contributor", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withSuper_NOT_FOUND() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/contributor/update/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withTheaterUser_403() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/contributor/update/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testUpdateContributor_withWrongUser_401() {
        ContributorSaveDto dto = new ContributorSaveDto("First", "Last", "Introduction");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ContributorSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/contributor/update/1000", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testSavePerformance_withSuper_NOT_FOUND() {
        List<PerformanceSaveDto> saveDtos = List.of(new PerformanceSaveDto(LocalDateTime.now(), 100L));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PerformanceSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/performance/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSavePerformance_withTheaterUser_403() {
        List<PerformanceSaveDto> saveDtos = List.of(new PerformanceSaveDto(LocalDateTime.now(), 100L));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PerformanceSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/performance/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSavePerformance_withWrongUser_401() {
        List<PerformanceSaveDto> saveDtos = List.of(new PerformanceSaveDto(LocalDateTime.now(), 100L));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PerformanceSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/performance/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testUpdatePerformance_withSuper_NOT_FOUND() {
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
    public void testSavePlay_withSuper_NOT_FOUND() {
        List<PlaySaveDto> playSaveDtoList = List.of(new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1,2,3), "drama"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PlaySaveDto>> request = new HttpEntity<>(playSaveDtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
                .exchange("/api/play/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSavePlay_withTheaterUser_403() {
        List<PlaySaveDto> playSaveDtoList = List.of(new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1,2,3), "drama"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<List<PlaySaveDto>> request = new HttpEntity<>(playSaveDtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser@testemail.te", "test")
                .exchange("/api/play/save", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testSavePlay_withWrongUser_401() {
        List<PlaySaveDto> playSaveDtoList = List.of(new PlaySaveDto("Play", "Plot", LocalDateTime.now(), 1L, List.of(1,2,3), "drama"));
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


}
