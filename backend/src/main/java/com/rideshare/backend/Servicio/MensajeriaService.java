package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Mensaje;
import com.rideshare.backend.Repositorio.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeriaService {

    private final MensajeRepository mensajeRepository;

    @Transactional
    public Mensaje enviarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajes(Integer trayectoId, Integer usuarioId) {
        return mensajeRepository.findMensajesByTrayectoAndUsuario(trayectoId, usuarioId);
    }

    @Transactional
    public void marcarComoLeidos(Integer trayectoId, Integer usuarioId) {
        List<Mensaje> mensajes = mensajeRepository.findMensajesByTrayectoAndUsuario(trayectoId, usuarioId);
        mensajes.stream()
                .filter(m -> !m.getLeido() && m.getDestinatarioId().equals(usuarioId))
                .forEach(m -> {
                    m.setLeido(true);
                    mensajeRepository.save(m);
                });
    }
}
