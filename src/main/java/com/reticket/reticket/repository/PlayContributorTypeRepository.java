package com.reticket.reticket.repository;

import com.reticket.reticket.domain.PlayContributorTypes;
import com.reticket.reticket.dto.list.ListDetailedContributorsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayContributorTypeRepository extends JpaRepository<PlayContributorTypes, Long> {


    @Query(value = "SELECT DISTINCT new com.reticket.reticket.dto.list.ListDetailedContributorsDto(p.contributor.firstName, " +
            "p.contributor.lastName, p.contributor.introduction) FROM PlayContributorTypes p ORDER BY p.contributor.lastName")
    List<ListDetailedContributorsDto> findAllContributors();

    @Query(value = "SELECT p.play.playName FROM PlayContributorTypes p WHERE p.contributor.firstName =:firstName AND " +
            "p.contributor.lastName =:lastName")
    List<String> findAllPlaysByContributors(String firstName, String lastName);


}
