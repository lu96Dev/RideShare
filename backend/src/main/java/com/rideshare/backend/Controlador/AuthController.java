package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Servicio.UsuarioService;
import com.rideshare.backend.TransferenciaDatos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public LoginResponse registro(@RequestBody RegistroRequest request) {

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Usuario guardado = usuarioService.registrar(usuario);

        return new LoginResponse(
                guardado.getId(),
                guardado.getNombre(),
                guardado.getEmail()
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.login(
                request.getEmail(),
                request.getPassword()
        ).orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        return new LoginResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
