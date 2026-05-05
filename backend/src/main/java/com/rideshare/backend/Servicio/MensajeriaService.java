package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Chat;
import com.rideshare.backend.Entidades.Mensaje;
import com.rideshare.backend.Repositorio.ChatRepository;
import com.rideshare.backend.Repositorio.MensajeriaRepository;
import com.rideshare.backend.TransferenciaDatos.MensajeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensajeriaService {

    private final MensajeriaRepository mensajeRepository;
    private final ChatRepository chatRepository;

    // 🔥 Ahora recibe y devuelve DTO
    public MensajeDTO enviarMensaje(MensajeDTO dto) {

        // Buscar el chat
        Chat chat = chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        // Crear entidad
        Mensaje mensaje = Mensaje.builder()
                .chat(chat)
                .remitenteId(dto.getRemitenteId())
                .contenido(dto.getContenido())
                .fechaEnvio(LocalDateTime.now())
                .leido(false)
                .build();

        // Guardar
        Mensaje guardado = mensajeRepository.save(mensaje);

        // Devolver DTO
        return convertirADTO(guardado);
    }

    public List<MensajeDTO> obtenerMensajes(Integer chatId) {
        return mensajeRepository.findByChatIdOrderByFechaEnvioAsc(chatId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MensajeDTO> obtenerMensajesNuevos(Integer chatId, Integer ultimoId) {
        return mensajeRepository.findByChatIdAndIdGreaterThanOrderByFechaEnvioAsc(chatId, ultimoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
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

    // 🔥 Convertir Entidad a DTO
    private MensajeDTO convertirADTO(Mensaje mensaje) {
        return MensajeDTO.builder()
                .id(mensaje.getId())
                .chatId(mensaje.getChat().getId())
                .remitenteId(mensaje.getRemitenteId())
                .contenido(mensaje.getContenido())
                .fechaEnvio(mensaje.getFechaEnvio())
                .leido(mensaje.getLeido())
                .build();
    }
}
