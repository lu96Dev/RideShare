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

        try {
            Usuario usuario = Usuario.builder()
                    .nombre(request.getNombre())
                    .apellidos(request.getApellidos())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .build();

            Usuario guardado = usuarioService.registrar(usuario);

            return new LoginResponse(
                    true,
                    "Registro completado correctamente",
                    guardado.getId(),
                    guardado.getNombre(),
                    guardado.getEmail()
            );

        } catch (Exception e) {
            return new LoginResponse(
                    false,
                    "El correo ya está registrado",
                    null,
                    null,
                    null
            );
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return usuarioService.login(request.getEmail(), request.getPassword())
                .map(usuario -> new LoginResponse(
                        true,
                        "Inicio de sesión correcto",
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail()
                ))
                .orElse(new LoginResponse(
                        false,
                        "Correo o contraseña incorrectos",
                        null,
                        null,
                        null
                ));
    }
}