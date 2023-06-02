package com.reticket.reticket;

import com.reticket.reticket.dto.report_search.FilterPerformancesDto;
import com.reticket.reticket.dto.report_search.PageableDto;
import com.reticket.reticket.dto.report_search.SearchDateDto;
import com.reticket.reticket.dto.save.AppUserSaveDto;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import com.reticket.reticket.dto.update.UpdateAppUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
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
    public void testCreateTheater_withWrongUser_400() {
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
    public void testUpdateTheater_withWrongUser_400() {
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
    public void testDeleteTheater_withWrongUser_400() {
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
    public void testUpdateAppUser_withWrongUser_400() {
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
        ResponseEntity<String> updateResult = template.withBasicAuth("super", "password")
                .exchange("/api/appUser/delete/username_7", HttpMethod.DELETE, updateRequest, String.class);
        assertEquals(HttpStatus.OK, updateResult.getStatusCode());
    }

}
