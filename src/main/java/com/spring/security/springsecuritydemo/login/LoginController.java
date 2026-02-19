package com.spring.security.springsecuritydemo.login;

import com.spring.security.springsecuritydemo.login.util.JwtUtil;
import com.spring.security.springsecuritydemo.usuario.Usuario;
import com.spring.security.springsecuritydemo.usuario.UsuarioService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping()
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final UsuarioService usuarioService;

    private final Counter adminCounter;


    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UsuarioService usuarioService, MeterRegistry meterRegistry, ObservationRegistry registry) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
        this.adminCounter = Counter.builder("rota_admin")
                .description("Contador de acessos ao endpoint /admin")
                .register(meterRegistry);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "Hello USER: ";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Observed(name = "adminEndpoint")
    public String adminEndpoint() {
        adminCounter.increment(1);
        log.info("Teste de log");
        usuarioService.buscarUsuarioPorEmail("diegodias@example.com");
        return "Hello ADMIN: ";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        var userDTO = new UserDTO();

        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password()));
        //Gerenerate Token
        String jwtToken = jwtUtil.generateJwtToken(auth);
        var usuarioLogado = (Usuario) auth.getPrincipal();
        BeanUtils.copyProperties(usuarioLogado, userDTO);
        userDTO.setRole(usuarioLogado.getRoles());
        return ResponseEntity.ok(
                new LoginResponse(HttpStatus.OK.getReasonPhrase(),
                        userDTO, jwtToken)
                );

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRegisterDTO loginRegisterDTO){

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(loginRegisterDTO, usuario);
        usuario.setSenha(passwordEncoder.encode(loginRegisterDTO.senha()));
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado com sucesso");
    }
}
