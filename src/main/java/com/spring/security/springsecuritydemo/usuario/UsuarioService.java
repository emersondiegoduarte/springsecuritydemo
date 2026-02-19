package com.spring.security.springsecuritydemo.usuario;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
//        return usuarioRepository.findByEmailIgnoreCase(username)
//                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
//    }

    public void salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        log.info("Buscando usuário por email: {}", email);
        return usuarioRepository.findByEmailIgnoreCase(email);
    }
}
