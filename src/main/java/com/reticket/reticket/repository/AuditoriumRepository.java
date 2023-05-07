package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
        Auditorium findAuditoriumByAuditoriumName(String auditoriumName);

        List<Auditorium> findAllByTheatre(Theatre theatre);

}
