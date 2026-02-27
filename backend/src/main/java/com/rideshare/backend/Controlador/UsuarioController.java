package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Servicio.UsuarioService;
import com.rideshare.backend.TransferenciaDatos.LoginResponse;
import com.rideshare.backend.TransferenciaDatos.SolicitudActualizacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable Integer id,
                                              @RequestBody SolicitudActualizacion solicitud) {
        try {
            // 1. Buscamos el usuario existente
            Usuario usuario = usuarioService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // 2. Seteamos la biografía que viene del móvil
            usuario.setBiografia(solicitud.getBiografia());

            // 3. Guardamos (usando el método guardar que añadimos o el repositorio)
            usuarioService.guardar(usuario);

            // 4. Respondemos con éxito para que Android no explote
            return ResponseEntity.ok(new LoginResponse(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getBiografia(),
                    true,
                    "¡Biografía guardada en la base de datos!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

