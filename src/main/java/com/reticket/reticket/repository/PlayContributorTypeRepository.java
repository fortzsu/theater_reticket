package com.reticket.reticket.repository;

import com.reticket.reticket.domain.PlayContributorTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayContributorTypeRepository extends JpaRepository<PlayContributorTypes, Long> {
}
