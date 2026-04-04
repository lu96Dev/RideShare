package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Trayecto;
import com.rideshare.backend.Servicio.TrayectoService;
import com.rideshare.backend.TransferenciaDatos.TrayectoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trayectos")
@RequiredArgsConstructor
public class TrayectoController {

    private final TrayectoService trayectoService;

    @PostMapping("/{usuarioId}")
    public Trayecto crear(@PathVariable Integer usuarioId,
                          @RequestBody TrayectoRequest request) {
        return trayectoService.crearTrayecto(request, usuarioId);
    }

    @GetMapping
    public List<Trayecto> listar() {
        return trayectoService.obtenerTrayectosActivos();
    }

    @PutMapping("/{id}/cerrar")
    public void cerrar(@PathVariable Integer id) {
        trayectoService.cerrarTrayecto(id);
    }
}