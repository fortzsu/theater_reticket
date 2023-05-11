package com.reticket.reticket.repository;

import com.reticket.reticket.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT a FROM AppUser a WHERE a.username = :username")
    AppUser findByUsername(String username);
}
