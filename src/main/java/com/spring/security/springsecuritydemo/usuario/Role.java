package com.spring.security.springsecuritydemo.usuario;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String nome; // ADMIN, ATENDENTE, USER

}
