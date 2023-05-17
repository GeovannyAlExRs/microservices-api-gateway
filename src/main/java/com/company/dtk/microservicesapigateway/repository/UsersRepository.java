package com.company.dtk.microservicesapigateway.repository;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.model.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    @Modifying
    @Query("update Users set role=:role where usename=:usename")
    void updateUserRole(@Param("username") String username, @Param("role") Roles role);
}
