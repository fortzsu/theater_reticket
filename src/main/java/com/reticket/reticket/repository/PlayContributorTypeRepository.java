package com.reticket.reticket.repository;

import com.reticket.reticket.domain.PlayContributorTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayContributorTypeRepository extends JpaRepository<PlayContributorTypes, Long> {


    @Query(value = "SELECT p FROM PlayContributorTypes p")
    List<PlayContributorTypes> findAllContributors();


}
