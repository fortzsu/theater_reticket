package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<AddressSaveDto> addressSaveDto) {
        this.addressService.save(addressSaveDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
