package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Chat;
import com.rideshare.backend.TransferenciaDatos.ChatPreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    Optional<Chat> findByTrayectoIdAndUsuario1IdAndUsuario2Id(
            Integer trayectoId,
            Integer usuario1Id,
            Integer usuario2Id
    );

    @Query("""
        SELECT new com.rideshare.backend.TransferenciaDatos.ChatPreview(
            c.id,
            c.trayectoId,
            CASE 
                WHEN c.usuario1Id = :usuarioId THEN c.usuario2Id 
                ELSE c.usuario1Id 
            END,
            '',
            '',
            0L
        )
        FROM Chat c
        WHERE c.usuario1Id = :usuarioId OR c.usuario2Id = :usuarioId
    """)
    List<ChatPreview> findChatsByUsuario(Integer usuarioId);
}