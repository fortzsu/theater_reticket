package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Auditorium;
import com.reticket.reticket.domain.Theater;
import com.reticket.reticket.dto.list.AuditoriumListDto;
import com.reticket.reticket.dto.save.AuditoriumSaveDto;
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
        @Query(value = "SELECT new com.reticket.reticket.dto.list.AuditoriumListDto(a.auditoriumName, a.id) FROM Auditorium a")
        List<AuditoriumListDto> findAllAuditorium();

}
