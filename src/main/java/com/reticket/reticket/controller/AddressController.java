package com.reticket.reticket.controller;

import com.reticket.reticket.dto.save.AddressSaveDto;
import com.reticket.reticket.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> save(@RequestBody List<AddressSaveDto> addressSaveDto) {
        this.addressService.save(addressSaveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MODIFY_IN_THEATER')")
    public ResponseEntity<Void> update(@RequestBody AddressSaveDto addressSaveDto, @PathVariable Long id) {
        this.addressService.update(addressSaveDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
