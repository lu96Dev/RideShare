package com.rideshare.backend.Servicio;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Setter
@Service
public class TypingService {

    private final Map<String, Long> typingMap = new ConcurrentHashMap<>();

    public void setTyping(Integer chatId, Integer userId) {
        typingMap.put(chatId + "_" + userId, System.currentTimeMillis());
    }

    public boolean isTyping(Integer chatId, Integer userId) {
        Long last = typingMap.get(chatId + "_" + userId);
        return last != null && (System.currentTimeMillis() - last < 3000);
    }
}
