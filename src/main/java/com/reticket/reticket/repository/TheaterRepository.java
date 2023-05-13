package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Theater;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    Theater findTheaterByTheaterName(String name);

    @Query(value = "SELECT t FROM Theater t")
    List<Theater> findAllTheater(Pageable pageable);

}
