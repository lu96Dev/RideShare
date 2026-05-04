package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MensajeriaRepository extends JpaRepository<Mensaje, Integer> {

    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findByChatIdOrderByFechaEnvioAsc(@Param("chatId") Integer chatId);

    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId AND m.id > :ultimoId ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findByChatIdAndIdGreaterThanOrderByFechaEnvioAsc(
            @Param("chatId") Integer chatId,
            @Param("ultimoId") Integer ultimoId
    );

    // ← CORREGIDO: devuelve Optional<Mensaje> con LIMIT 1
    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId ORDER BY m.fechaEnvio DESC LIMIT 1")
    Optional<Mensaje> findTopByChatIdOrderByFechaEnvioDesc(@Param("chatId") Integer chatId);

    @Query("""
    SELECT COUNT(m)
    FROM Mensaje m
    WHERE m.chat.id = :chatId
    AND m.remitenteId <> :usuarioId
    AND m.leido = false
    """)
    Long countUnread(@Param("chatId") Integer chatId,
                     @Param("usuarioId") Integer usuarioId);

    @Transactional
    void deleteByChatId(Integer chatId);
}



