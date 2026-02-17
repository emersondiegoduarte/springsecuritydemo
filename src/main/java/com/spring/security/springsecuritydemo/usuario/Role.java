package com.spring.security.springsecuritydemo.usuario;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // ADMIN, ATENDENTE, USER

    public String getNome() {
        return nome;
    }
}
