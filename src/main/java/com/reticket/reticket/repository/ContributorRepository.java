package com.reticket.reticket.repository;

import com.reticket.reticket.domain.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    List<Contributor> findByOrderByLastName();

    @Query(value = "SELECT * FROM contributor JOIN play_contributor_types p " +
            "ON (contributor.contributor_id = p.contributor_contributor_id)" +
            "WHERE play_play_id =:playId",
            nativeQuery = true)
    List<Contributor> findContributorsByPlay(@Param("playId") Long playId);




}
