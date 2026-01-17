package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Trayecto;
import com.rideshare.backend.Repositorio.TrayectoRepository;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrayectoService {

    private final TrayectoRepository trayectoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Trayecto crearTrayecto(Trayecto trayecto, Integer conductorId) {

        usuarioRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        trayecto.setConductorId(conductorId);
        trayecto.setActivo(true);

        return trayectoRepository.save(trayecto);
    }

    public List<Trayecto> obtenerTrayectosActivos() {
        return trayectoRepository.findByActivoTrue();
    }

    public void cerrarTrayecto(Integer trayectoId) {
        trayectoRepository.findById(trayectoId).ifPresent(t -> {
            t.setActivo(false);
            trayectoRepository.save(t);
        });
    }
}
