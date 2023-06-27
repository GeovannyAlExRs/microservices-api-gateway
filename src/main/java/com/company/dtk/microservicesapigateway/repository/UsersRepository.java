package com.company.dtk.microservicesapigateway.repository;

import com.company.dtk.microservicesapigateway.model.Roles;
import com.company.dtk.microservicesapigateway.model.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    @Modifying
    @Query("update Users set role=:role where username=:username")
    void updateUserRole(@Param("username") String username, @Param("role") Roles role);
}
