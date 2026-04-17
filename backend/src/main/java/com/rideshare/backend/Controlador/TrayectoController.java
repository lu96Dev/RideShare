package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Trayecto;
import com.rideshare.backend.Servicio.TrayectoService;
import com.rideshare.backend.TransferenciaDatos.TrayectoRequest;
import com.rideshare.backend.TransferenciaDatos.BusquedaTrayectoRequest; // Asegúrate de crear este DTO
import com.rideshare.backend.TransferenciaDatos.BusquedaTrayectoResponse; // Opcional si quieres envolver la lista
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trayectos")
@RequiredArgsConstructor
public class TrayectoController {

    private final TrayectoService trayectoService;

    // PUBLICAR: api/trayectos/{usuarioId}
    @PostMapping("/{usuarioId}")
    public Trayecto crear(@PathVariable Integer usuarioId,
                          @RequestBody TrayectoRequest request) {
        return trayectoService.crearTrayecto(request, usuarioId);
    }

    // LISTAR TODOS: api/trayectos
    @GetMapping
    public List<Trayecto> listar() {
        return trayectoService.obtenerTrayectosActivos();
    }

    // CERRAR: api/trayectos/{id}/cerrar
    @PutMapping("/{id}/cerrar")
    public void cerrar(@PathVariable Integer id) {
        trayectoService.cerrarTrayecto(id);
    }

    // --- NUEVO METODO DE BÚSQUEDA POR RADIO ---
    // URL: api/trayectos/buscar
    @PostMapping("/buscar")
    public List<Trayecto> buscarCercanos(@RequestBody BusquedaTrayectoRequest request) {
        return trayectoService.buscarViajesCercanos(
                request.getLatitud(),
                request.getLongitud(),
                request.getRadioKm()
        );
    }
}