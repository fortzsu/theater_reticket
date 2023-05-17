package com.reticket.reticket.security.repository_service;

import com.reticket.reticket.security.RoleEnum;
import com.reticket.reticket.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    @Query(value = "SELECT u FROM UserRole u WHERE u.roleEnum = :roleEnum")
    UserRole findUserRoleByRoleEnum(RoleEnum roleEnum);


}
