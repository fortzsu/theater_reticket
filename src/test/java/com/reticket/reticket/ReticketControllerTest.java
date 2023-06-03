package com.reticket.reticket;

import com.reticket.reticket.dto.list.LikedPlaysListDto;
import com.reticket.reticket.dto.list.ListTicketDto;
import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.FilterReportDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.save.TicketActionDto;
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
public class ReticketControllerTest {


    @Autowired
    private TestRestTemplate template;

    @Test
    public void testListAuditorium_withSuper_200() {
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_1", "password", "Guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void testSaveGuest_withNoUser_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username1", "password", "Guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSaveGuest_withWrongUser_401() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username2", "password", "Guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }


    @Test
    public void testCreateTheater_withSuper_200() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<Boolean> result = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
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
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_2", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("email@gmail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("super", "test")
                .exchange("/api/appUser/update/username_2", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withGuest_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_3", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("email@gmail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_3", "password")
                .exchange("/api/appUser/update/username_3", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withWrongUser_401() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_4", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("email@gmail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("wrong", "password")
                .exchange("/api/appUser/update/username_4", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, updateResult.getStatusCode());
    }

    @Test
    public void testUpdateAppUser_withWithGuest_NOT_FOUND() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_5", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        UpdateAppUserDto updateAppUserDto = new UpdateAppUserDto("email@gmail.com", "password");
        HttpEntity<UpdateAppUserDto> updateRequest = new HttpEntity<>(updateAppUserDto, updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_5", "password")
                .exchange("/api/appUser/update/username_1000", HttpMethod.PUT, updateRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, updateResult.getStatusCode());
    }

    @Test
    public void testDeleteAppUser_withGuest_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_6", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        HttpEntity<Boolean> updateRequest = new HttpEntity<>(updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("username_6", "password")
                .exchange("/api/appUser/delete/username_6", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testDeleteAppUser_withSuper_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_7", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders updateHeaders = new HttpHeaders();
        HttpEntity<Boolean> updateRequest = new HttpEntity<>(updateHeaders);
        ResponseEntity<String> updateResult = template.withBasicAuth("super", "test")
                .exchange("/api/appUser/delete/username_7", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

    @Test
    public void testAppUserListTickets_withSuper_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_8", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<ListTicketDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("super", "test")
                .exchange("/api/appUser/username_8/tickets", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListTickets_withGuest_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_9", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<ListTicketDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_9", "password")
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
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_10", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("super", "test")
                .exchange("/api/appUser/username_10/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListLikedPlays_withGuest_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_11", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_11", "password")
                .exchange("/api/appUser/username_11/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.OK, listResult.getStatusCode());
    }

    @Test
    public void testAppUserListLikedPlays_withWrongUser_401() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_11", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<List<LikedPlaysListDto>> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("wrong", "password")
                .exchange("/api/appUser/username_11/likedPlays", HttpMethod.GET, listRequest, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, listResult.getStatusCode());
    }

    @Test
    public void testAppUserLikePlay_withGuest_200() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_12", "password",
                "guest", "email@gmail.com");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AppUserSaveDto> request = new HttpEntity<>(dto, headers);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", request, String.class);
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<Void> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("username_12", "password")
                .exchange("/api/appUser/username_12/1", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, listResult.getStatusCode());
    }

    @Test
    public void testAppUserLikePlay_withSuper_403() {
        HttpHeaders listHeaders = new HttpHeaders();
        HttpEntity<Void> listRequest = new HttpEntity<>(listHeaders);
        ResponseEntity<String> listResult = template.withBasicAuth("super", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testTicketAction_withTheaterUser_NOT_FOUND() {
        HttpHeaders headers = new HttpHeaders();
        List<TicketActionDto> dtoList = List.of(new TicketActionDto("buy", "user", List.of(1L,2L)));
        HttpEntity<List<TicketActionDto>> listRequest = new HttpEntity<>(dtoList, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser", "test")
                .exchange("/api/ticketAction", HttpMethod.POST, listRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testReport_withGuestUser_403() {
        AppUserSaveDto dto = new AppUserSaveDto("First", "Last", "username_13", "password",
                "guest", "email@gmail.com");
        HttpHeaders guestHeaders = new HttpHeaders();
        HttpEntity<AppUserSaveDto> guestRequest = new HttpEntity<>(dto, guestHeaders);
        template.withBasicAuth("super", "test")
                .postForEntity("/api/appUser/saveGuest", guestRequest, String.class);
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("username_13", "password")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void testReport_withSuperUser_200() {
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .exchange("/api/report", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testReport_withTheaterAdmin_200() {
        HttpHeaders headers = new HttpHeaders();
        FilterReportDto filterReportDto = new FilterReportDto("SOLD", "theater", 1L,
                new SearchDateDto(2023,5,1,2023,5,1), true, false);
        HttpEntity<FilterReportDto> request = new HttpEntity<>(filterReportDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterAdmin", "test")
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
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .exchange("/api/address", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testAddress_withTheaterUser_403() {
        HttpHeaders headers = new HttpHeaders();
        List<AddressSaveDto> saveDtos = List.of(new AddressSaveDto("1000", "City", "Street", 123, 1L));
        HttpEntity<List<AddressSaveDto>> request = new HttpEntity<>(saveDtos, headers);
        ResponseEntity<String> result = template.withBasicAuth("theaterUser", "test")
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
        AddressSaveDto saveDto = new AddressSaveDto();
        HttpEntity<AddressSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .exchange("/api/address/update/1", HttpMethod.PUT, request, String.class);
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







}
