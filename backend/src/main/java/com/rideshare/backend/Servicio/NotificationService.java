package com.rideshare.backend.Servicio;

import com.rideshare.backend.model.Notificacion;
import com.rideshare.backend.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public void enviarNotificacionMensaje(Integer usuarioId, Integer remitenteId, String contenido) {
        Notificacion notificacion = Notificacion.builder()
                .usuarioId(usuarioId)
                .tipo("MENSAJE")
                .titulo("Nuevo mensaje")
                .mensaje(contenido)
                .leida(false)
                .build();
        notificacionRepository.save(notificacion);
    }
}
