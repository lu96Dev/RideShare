package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Servicio.UsuarioService;
import com.rideshare.backend.TransferenciaDatos.LoginResponse;
import com.rideshare.backend.TransferenciaDatos.SolicitudActualizacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable Integer id,
                                              @RequestBody SolicitudActualizacion solicitud) {
        try {
            // 1. Buscamos el usuario existente.
            // Si el ID que envía Android (el idUsuarioLogueado = 1) no existe, entrará por aquí.
            Usuario usuario = usuarioService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe en la base de datos"));

            // 2. Seteamos la biografía que viene del móvil
            usuario.setBiografia(solicitud.getBiografia());

            // 3. Persistimos los cambios en MySQL
            usuarioService.guardar(usuario);

            // 4. Devolvemos LoginResponse.
            // Es vital que los campos 'correcto' y 'mensaje' coincidan con tu DTO en Android.
            return ResponseEntity.ok(new LoginResponse(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getBiografia(),
                    true,
                    "¡Biografía guardada correctamente!"
            ));

        } catch (Exception e) {
            // IMPORTANTE: Si hay error, devolvemos un objeto con 'correcto: false'
            // para que el Toast de Android tenga un texto que mostrar y no sea NULL.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, null, null, false, "Error en servidor: " + e.getMessage()));
        }
    }
}

