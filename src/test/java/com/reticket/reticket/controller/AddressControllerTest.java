package com.reticket.reticket.controller;


import com.reticket.reticket.dto.save.AddressSaveDto;
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
public class AddressControllerTest {

    @Autowired
    private TestRestTemplate template;


//    @Test
//    public void testAddress_withSuper_200() {
//        HttpHeaders headers = new HttpHeaders();
//        List<AddressSaveDto> saveDtos = List.of(new AddressSaveDto("1000", "City", "Street", 123, 1L));
//        HttpEntity<List<AddressSaveDto>> request = new HttpEntity<>(saveDtos, headers);
//        ResponseEntity<String> result = template.withBasicAuth("reticket23@gmail.com", "test")
//                .exchange("/api/address", HttpMethod.POST, request, String.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//    }

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
    public void testUpdateAddress_withWrongUser_401() {
        HttpHeaders headers = new HttpHeaders();
        AddressSaveDto saveDto = new AddressSaveDto();
        HttpEntity<AddressSaveDto> request = new HttpEntity<>(saveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("wrong", "test")
                .exchange("/api/address/1", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
