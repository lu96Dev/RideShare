package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Usuario actualizarPerfil(Integer id, Usuario datos) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(datos.getNombre());
            u.setApellidos(datos.getApellidos());
            u.setTelefono(datos.getTelefono());
            u.setBiografia(datos.getBiografia());
            u.setPreferenciasViaje(datos.getPreferenciasViaje());
            u.setFotoPerfil(datos.getFotoPerfil());
            return usuarioRepository.save(u);
        }).orElseThrow();
    }
}