package com.company.dtk.microservicesapigateway.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 100)
    private  String username;

    @Column(name = "password", nullable = false)
    private  String password;

    @Column(name = "name", nullable = false)
    private  String name;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;
}
