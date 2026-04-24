package com.rideshare.backend.Controlador;

import com.rideshare.backend.Entidades.Chat;
import com.rideshare.backend.Servicio.ChatService;
import com.rideshare.backend.TransferenciaDatos.ChatRequest;
import com.rideshare.backend.TransferenciaDatos.ChatPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/crear")
    public Chat crear(@RequestBody ChatRequest dto) {
        return chatService.crearOCrearChat(dto);
    }

    @GetMapping("/{usuarioId}")
    public List<ChatPreview> listar(@PathVariable Integer usuarioId) {
        return chatService.getChatsUsuario(usuarioId);
    }
}
