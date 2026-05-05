package com.rideshare.backend.Controlador;

import com.rideshare.backend.Servicio.TypingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typing")
@RequiredArgsConstructor
public class TypingController{
    private final TypingService typingService;
    @PostMapping("/{chatId}/typing")
    public void typing(@PathVariable Integer chatId,
                       @RequestParam Integer userId) {
        typingService.setTyping(chatId, userId);
    }
    @GetMapping("/{chatId}/typing")
    public boolean isTyping(@PathVariable Integer chatId,
                            @RequestParam Integer userId) {
        return typingService.isTyping(chatId, userId);
    }
}