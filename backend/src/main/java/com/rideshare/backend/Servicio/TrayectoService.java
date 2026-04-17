package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Trayecto;
import com.rideshare.backend.Repositorio.TrayectoRepository;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import com.rideshare.backend.TransferenciaDatos.TrayectoRequest;
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
    public Trayecto crearTrayecto(TrayectoRequest request, Integer conductorId) {
        // 1. Validar usuario
        usuarioRepository.findById(conductorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear objeto Trayecto con coordenadas
        Trayecto trayecto = Trayecto.builder()
                .conductorId(conductorId)
                .origen(request.getOrigen())
                .origenLat(request.getOrigenLat())
                .origenLng(request.getOrigenLng())
                .destino(request.getDestino())
                .fecha(request.getFecha())
                .hora(request.getHora())
                .descripcion(request.getDescripcion())
                .activo(true)
                .build();

        // 3. Guardar en BD
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

    // --- NUEVO METODO: SOLUCIONA EL ERROR EN EL CONTROLADOR ---
    public List<Trayecto> buscarViajesCercanos(Double latitud, Double longitud, Double radioKm) {
        return trayectoRepository.encontrarTrayectosEnRadio(latitud, longitud, radioKm);
    }
}