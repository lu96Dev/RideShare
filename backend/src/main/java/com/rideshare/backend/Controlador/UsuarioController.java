package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Servicio.UsuarioService;
import lombok.RequiredArgsConstructor;
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
    public Usuario actualizarPerfil(@PathVariable Integer id,
                                    @RequestBody Usuario usuario) {
        return usuarioService.actualizarPerfil(id, usuario);
    }
}

