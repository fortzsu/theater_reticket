package com.reticket.reticket.repository;

import com.reticket.reticket.domain.AppUser;
import com.reticket.reticket.domain.Contributor;
import com.reticket.reticket.domain.Play;
import com.reticket.reticket.dto.list.ListPlaysDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {

        @Query(value = "SELECT p FROM Play p JOIN p.appUsers a WHERE a = :appUser")
        List<Play> findPlaysByAppUser(@Param("appUser") AppUser appUser);

        @Query(value = "SELECT new com.reticket.reticket.dto.list.ListPlaysDto (p.playName, p.plot, p.premiere, p.playType) FROM Play p")
        List<ListPlaysDto> findAllPlay(Pageable pageable);

}
