package com.spring.security.springsecuritydemo.security;

import com.spring.security.springsecuritydemo.usuario.Usuario;
import com.spring.security.springsecuritydemo.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //Aqui você pode implementar a lógica de autenticação, como verificar o username e password
        //com um banco de dados ou outro serviço de autenticação.

        //Se a autenticação for bem-sucedida, retorne um objeto Authentication válido.
        //Caso contrário, retorne null ou lance uma AuthenticationException.
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                .toList();

        if (passwordEncoder.matches(password, usuario.getSenha())) {
            return new UsernamePasswordAuthenticationToken(usuario, null, authorities);
        } else {
            throw new BadCredentialsException("Usuário inválido!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
