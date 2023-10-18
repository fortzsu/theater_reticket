package com.reticket.reticket.service;

import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.Price;
import com.reticket.reticket.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceService {


    private PriceRepository priceRepository;
    public List<Price> findAllByPlay(Play play) {
        return this.priceRepository.findByPlay(play);
    }





}
