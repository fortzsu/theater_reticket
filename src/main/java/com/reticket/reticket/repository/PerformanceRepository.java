package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Performance;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.domain.enums.PlayType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    @Query(value = "SELECT p FROM Performance p WHERE p.play = :play AND p.isAvailableOnline = true " +
            "AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false ORDER BY p.originalPerformanceDateTime")
    List<Performance> findUpcomingPerformancesByPlayId(Play play);


}
