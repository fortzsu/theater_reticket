package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByPlay(Play play);

}
