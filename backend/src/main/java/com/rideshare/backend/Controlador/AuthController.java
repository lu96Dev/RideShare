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

            System.out.println("REGISTRO EMAIL: " + request.getEmail());
            System.out.println("REGISTRO PASS: " + request.getPassword());

            Usuario usuario = Usuario.builder()
                    .nombre("Usuario")
                    .apellidos("Nuevo")
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

        }  catch (org.springframework.dao.DataIntegrityViolationException e) {

            return new LoginResponse(
                    false,
                    "Este correo ya está registrado",
                    null,
                    null,
                    null
            );

        } catch (RuntimeException e) {

            if(e.getMessage().equals("EMAIL_EXISTE")){

                return new LoginResponse(
                        false,
                        "Este correo ya está registrado",
                        null,
                        null,
                        null
                );
            }

            return new LoginResponse(
                    false,
                    "Error al registrar usuario",
                    null,
                    null,
                    null
            );
        } catch (Exception e) {

            e.printStackTrace();

            return new LoginResponse(
                    false,
                    "No se ha podido realizar el registro",
                    null,
                    null,
                    null
            );
        }

    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("EMAIL: " + request.getEmail());
        System.out.println("PASS: " + request.getPassword());

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