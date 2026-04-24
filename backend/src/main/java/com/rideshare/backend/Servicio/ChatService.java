package com.rideshare.backend.Servicio;

import com.rideshare.backend.Entidades.Chat;
import com.rideshare.backend.Repositorio.ChatRepository;
import com.rideshare.backend.TransferenciaDatos.ChatRequest;
import com.rideshare.backend.TransferenciaDatos.ChatPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat crearOCrearChat(ChatRequest dto) {

        // 🔥 IMPORTANTE: evitar duplicados sin importar orden
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
        return chatRepository.findChatsByUsuario(usuarioId);
    }
}