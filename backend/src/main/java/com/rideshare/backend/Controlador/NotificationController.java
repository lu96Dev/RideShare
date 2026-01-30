package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Notificacion;
import com.rideshare.backend.Servicio.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificacionService service;

    @GetMapping("/{usuarioId}")
    public List<Notificacion> obtener(@PathVariable Integer usuarioId) {
        return service.obtenerNotificaciones(usuarioId);
    }

    @PutMapping("/{id}/leida")
    public void marcarLeida(@PathVariable Integer id) {
        service.marcarComoLeida(id);
    }
}
