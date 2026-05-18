package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> actualizarPerfil(
            @PathVariable Integer id,
            @RequestBody Map<String, String> datos) {
        try {
            Usuario actualizado = usuarioService.actualizarPerfil(id, datos);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Perfil actualizado correctamente",
                    "usuario", actualizado
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", "Error: " + e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Map<String, Object>> cambiarPassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> datos) {
        try {
            String passwordActual = datos.get("passwordActual");
            String passwordNueva = datos.get("passwordNueva");

            usuarioService.cambiarPassword(id, passwordActual, passwordNueva);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}