package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Mensaje;
import com.rideshare.backend.Repositorio.MensajeriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeriaService {

    private final MensajeriaRepository mensajeRepository;

    public Mensaje enviarMensaje(Mensaje mensaje) {
        if (mensaje.getLeido() == null) {
            mensaje.setLeido(false);
        }
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerMensajes(Integer chatId) {
        return mensajeRepository.findByChatIdOrderByFechaEnvioAsc(chatId);
    }

    public List<Mensaje> obtenerMensajesNuevos(Integer chatId, Integer ultimoId) {
        return mensajeRepository.findByChatIdAndIdGreaterThanOrderByFechaEnvioAsc(chatId, ultimoId);
    }

    public void marcarComoLeidos(Integer chatId, Integer usuarioId) {
        List<Mensaje> mensajes = mensajeRepository.findByChatIdOrderByFechaEnvioAsc(chatId);

        mensajes.stream()
                .filter(m -> !m.getLeido() && !m.getRemitenteId().equals(usuarioId))
                .forEach(m -> {
                    m.setLeido(true);
                    mensajeRepository.save(m);
                });
    }
}
