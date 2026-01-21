package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Mensaje;
import com.rideshare.backend.Servicio.MensajeriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeriaService service;

    @PostMapping
    public Mensaje enviar(@RequestBody Mensaje mensaje) {
        return service.enviarMensaje(mensaje);
    }

    @GetMapping("/{trayectoId}/{usuarioId}")
    public List<Mensaje> obtener(@PathVariable Integer trayectoId,
                                 @PathVariable Integer usuarioId) {
        return service.obtenerMensajes(trayectoId, usuarioId);
    }

    @PutMapping("/{trayectoId}/leidos/{usuarioId}")
    public void marcarLeidos(@PathVariable Integer trayectoId,
                             @PathVariable Integer usuarioId) {
        service.marcarComoLeidos(trayectoId, usuarioId);
    }
}
