package com.reticket.reticket;


import com.reticket.reticket.controller.TheaterController;
import com.reticket.reticket.dto.save.TheaterSaveDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReticketControllerTest {


    @Autowired
    private TestRestTemplate template;

    @Test
    public void testListAuditorium_withSuper_ok() {
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .getForEntity("/api/auditorium/list", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testCreateTheater_withSuper_ok() {
        TheaterSaveDto theaterSaveDto = new TheaterSaveDto("Name", "Story");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TheaterSaveDto> request = new HttpEntity<>(theaterSaveDto, headers);
        ResponseEntity<String> result = template.withBasicAuth("super", "test")
                .postForEntity("/api/theater/create", request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }





}
