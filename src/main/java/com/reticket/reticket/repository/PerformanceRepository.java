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

    @Query(value = "SELECT p FROM Performance p WHERE p.isAvailableOnline = true " +
            "AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformances(Pageable pageable);

    @Query(value = "SELECT p FROM Performance p WHERE p.play.auditorium.theater = :theater AND p.isAvailableOnline = true" +
            " AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false " +
            "AND p.performanceDateTime >= :startDate AND p.performanceDateTime <= :endDate ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformancesByTheaterId(Theater theater, Pageable pageable,
                                                          LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Performance p WHERE p.play.auditorium = :auditorium AND p.isAvailableOnline = true" +
            " AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false " +
            "AND p.performanceDateTime >= :startDate AND p.performanceDateTime <= :endDate ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformancesByAuditoriumId(Auditorium auditorium, Pageable pageable,
                                                             LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Performance p WHERE p.play = :play AND p.isAvailableOnline = true " +
            "AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false " +
            "AND p.performanceDateTime >= :startDate AND p.performanceDateTime <= :endDate ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformancesByPlayIdPage(Play play, Pageable pageable,
                                                           LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Performance p WHERE p.play.playType = :playType AND p.isAvailableOnline = true " +
            "AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false " +
            "AND p.performanceDateTime >= :startDate AND p.performanceDateTime <= :endDate ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformancesByPlayType(PlayType playType, Pageable pageable,
                                                         LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p FROM Performance p WHERE p.play = :play AND p.isAvailableOnline = true " +
            "AND p.isCancelled = false AND p.isSeenOnline = true AND p.isSold = false ORDER BY p.performanceDateTime")
    List<Performance> findUpcomingPerformancesByPlayId(Play play);


}
