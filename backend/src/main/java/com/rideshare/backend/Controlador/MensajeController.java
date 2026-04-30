package com.rideshare.backend.Controlador;

import com.rideshare.backend.Servicio.MensajeriaService;
import com.rideshare.backend.TransferenciaDatos.MensajeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeriaService service;

    @PostMapping
    public MensajeDTO enviar(@RequestBody MensajeDTO mensaje) {
        return service.enviarMensaje(mensaje);
    }

    @GetMapping("/{chatId}")
    public List<MensajeDTO> obtener(@PathVariable Integer chatId,
                                    @RequestParam(required = false) Integer ultimoId) {

        if (ultimoId == null) {
            return service.obtenerMensajes(chatId);
        } else {
            return service.obtenerMensajesNuevos(chatId, ultimoId);
        }
    }

    @PutMapping("/{chatId}/leidos/{usuarioId}")
    public void marcarLeidos(@PathVariable Integer chatId,
                             @PathVariable Integer usuarioId) {
        service.marcarComoLeidos(chatId, usuarioId);
    }
}
