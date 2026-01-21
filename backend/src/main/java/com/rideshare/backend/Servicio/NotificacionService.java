package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Notificacion;
import com.rideshare.backend.Repositorio.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public void enviarNotificacionMensaje(Integer usuarioId) {

        Notificacion notificacion = Notificacion.builder()
                .usuarioId(usuarioId)
                .tipo(Notificacion.TipoNotificacion.MENSAJE)
                .titulo("Nuevo mensaje")
                .mensaje("Tienes un mensaje nuevo en RideShare")
                .build();

        notificacionRepository.save(notificacion);

        enviarPushNotification(usuarioId, notificacion.getTitulo(), notificacion.getMensaje());
    }

    private void enviarPushNotification(Integer usuarioId, String titulo, String mensaje) {
        // TODO: Firebase
    }

    public List<Notificacion> obtenerNotificaciones(Integer usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaEnvioDesc(usuarioId);
    }

    public List<Notificacion> obtenerNotificacionesNoLeidas(Integer usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeida(usuarioId, false);
    }

    public Long contarNotificacionesNoLeidas(Integer usuarioId) {
        return notificacionRepository.countNotificacionesNoLeidas(usuarioId);
    }

    public void marcarComoLeida(Integer notificacionId) {
        notificacionRepository.marcarComoLeida(notificacionId);
    }

    public void marcarTodasComoLeidas(Integer usuarioId) {
        notificacionRepository.marcarTodasComoLeidas(usuarioId);
    }

    public void eliminarNotificacionesAntiguas(Integer usuarioId, int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        notificacionRepository.eliminarNotificacionesAntiguas(usuarioId, fechaLimite);
    }
}
