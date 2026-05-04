package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Chat;
import com.rideshare.backend.Repositorio.ChatRepository;
import com.rideshare.backend.Repositorio.MensajeriaRepository;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import com.rideshare.backend.TransferenciaDatos.ChatPreview;
import com.rideshare.backend.TransferenciaDatos.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MensajeriaRepository mensajeriaRepository;
    private final UsuarioRepository usuarioRepository;

    public Chat crearOCrearChat(ChatRequest dto) {
        Integer u1 = Math.min(dto.getUsuario1Id(), dto.getUsuario2Id());
        Integer u2 = Math.max(dto.getUsuario1Id(), dto.getUsuario2Id());

        return chatRepository.findByTrayectoIdAndUsuario1IdAndUsuario2Id(
                dto.getTrayectoId(), u1, u2
        ).orElseGet(() -> {
            Chat chat = Chat.builder()
                    .trayectoId(dto.getTrayectoId())
                    .usuario1Id(u1)
                    .usuario2Id(u2)
                    .build();
            return chatRepository.save(chat);
        });
    }

    public List<ChatPreview> getChatsUsuario(Integer usuarioId) {
        List<ChatPreview> previews = chatRepository.findChatsByUsuario(usuarioId);

        for (ChatPreview preview : previews) {
            Integer otroId = preview.getOtroUsuarioId();

            // 1. Nombre y foto del otro usuario
            usuarioRepository.findById(otroId).ifPresent(usuario -> {
                preview.setNombre(usuario.getNombre() + " " + usuario.getApellidos());
                preview.setFotoPerfil(usuario.getFotoPerfil());
            });

            // 2. Último mensaje
            mensajeriaRepository
                    .findTopByChatIdOrderByFechaEnvioDesc(preview.getChatId())
                    .ifPresent(ultimo -> preview.setUltimoMensaje(ultimo.getContenido()));

            // 3. No leídos
            Long unread = mensajeriaRepository.countUnread(preview.getChatId(), usuarioId);
            preview.setUnreadCount(unread != null ? unread : 0L);
        }

        return previews;
    }

    @Transactional
    public void eliminarChat(Integer chatId) {
        mensajeriaRepository.deleteByChatId(chatId); // primero los mensajes
        chatRepository.deleteById(chatId);            // luego el chat
    }
}