package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
        Auditorium findAuditoriumByAuditoriumName(String auditoriumName);

        @Query(value = "SELECT a FROM Auditorium a WHERE a.theater =:theater")
        List<Auditorium> findAllByTheater(Theater theater);

}
