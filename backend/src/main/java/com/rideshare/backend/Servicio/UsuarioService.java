package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("EMAIL_EXISTE");
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarPerfil(Integer id, Map<String, String> datos) {
        return usuarioRepository.findById(id).map(u -> {
            // Solo actualiza los campos que vengan en el Map
            if (datos.containsKey("nombre")) {
                u.setNombre(datos.get("nombre"));
            }
            if (datos.containsKey("apellidos")) {
                u.setApellidos(datos.get("apellidos"));
            }
            if (datos.containsKey("telefono")) {
                u.setTelefono(datos.get("telefono"));
            }
            if (datos.containsKey("biografia")) {
                u.setBiografia(datos.get("biografia"));
            }
            if (datos.containsKey("preferenciasViaje")) {
                u.setPreferenciasViaje(datos.get("preferenciasViaje"));
            }
            if (datos.containsKey("fotoPerfil")) {
                u.setFotoPerfil(datos.get("fotoPerfil"));
            }
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void cambiarPassword(Integer id, String passwordActual, String passwordNueva) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que la contraseña actual sea correcta
        if (!usuario.getPassword().equals(passwordActual)) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        // Validar nueva contraseña
        if (passwordNueva == null || passwordNueva.length() < 4) {
            throw new RuntimeException("La nueva contraseña debe tener al menos 4 caracteres");
        }

        usuario.setPassword(passwordNueva);
        usuarioRepository.save(usuario);
    }
}