package com.reticket.reticket.security.repository_service;

import com.reticket.reticket.security.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {
}
