package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByAuditoriumId(Auditorium auditoriumId);


}
