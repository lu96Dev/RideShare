package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Mensaje;
import com.rideshare.backend.Repositorio.MensajeriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeriaService {

    private final MensajeriaRepository mensajeRepository;

    @Transactional
    public Mensaje enviarMensaje(Mensaje mensaje) {
        if (mensaje.getLeido() == null) {
            mensaje.setLeido(false);
        }
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerChatsUsuario(Integer usuarioId) {
        return mensajeRepository.findChatsUsuario(usuarioId);
    }

    public List<Mensaje> obtenerMensajes(Integer trayectoId, Integer usuarioId) {
        return mensajeRepository.findMensajesByTrayectoAndUsuario(trayectoId, usuarioId);
    }
    public List<Mensaje> obtenerMensajesNuevos(Integer trayectoId, Integer usuarioId, Integer ultimoId) {
        return mensajeRepository.findMensajesNuevos(trayectoId, usuarioId, ultimoId);
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
