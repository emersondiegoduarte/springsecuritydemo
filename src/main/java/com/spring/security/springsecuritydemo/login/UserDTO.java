package com.spring.security.springsecuritydemo.login;

import com.spring.security.springsecuritydemo.usuario.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter()
@Setter
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private Set<Role> role;
}
