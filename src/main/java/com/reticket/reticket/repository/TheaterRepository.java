package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    Theater findTheaterByTheaterName(String name);

}
