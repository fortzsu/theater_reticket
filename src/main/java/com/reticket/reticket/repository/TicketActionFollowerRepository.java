package com.reticket.reticket.repository;

import com.reticket.reticket.domain.TicketActionFollowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketActionFollowerRepository extends JpaRepository<TicketActionFollowerEntity, Long> {

}
