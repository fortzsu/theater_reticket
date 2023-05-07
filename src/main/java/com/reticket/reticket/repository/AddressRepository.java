package com.reticket.reticket.repository;

import com.reticket.reticket.domain.AddressEntity;
import com.reticket.reticket.domain.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByAuditoriumId(Auditorium auditoriumId);

}
