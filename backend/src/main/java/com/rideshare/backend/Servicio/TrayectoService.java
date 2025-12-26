package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Trayecto;
import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Repositorio.TrayectoRepository;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrayectoService {

    private final TrayectoRepository trayectoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Trayecto crearTrayecto(Trayecto trayecto, Integer conductorId) {
        Usuario conductor = usuarioRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
        trayecto.setConductorId(conductorId);
        trayecto.setPlazasDisponibles(trayecto.getPlazasTotales());
        return trayectoRepository.save(trayecto);
    }

    public List<Trayecto> obtenerTrayectosDisponibles() {
        return trayectoRepository.findByFechaAfterAndEstado(LocalDate.now(), "ACTIVO");
    }
}
