package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeriaRepository extends JpaRepository<Mensaje, Integer> {

    List<Mensaje> findByChatIdOrderByFechaEnvioAsc(Integer chatId);

    List<Mensaje> findByChatIdAndIdGreaterThanOrderByFechaEnvioAsc(
            Integer chatId,
            Integer ultimoId
    );

    Mensaje findTopByChatIdOrderByFechaEnvioDesc(Integer chatId);

    @Query("""
    SELECT COUNT(m)
    FROM Mensaje m
    WHERE m.chat.id = :chatId
    AND m.remitenteId <> :usuarioId
    AND m.leido = false
    """)
    Long countUnread(@Param("chatId") Integer chatId,
                     @Param("usuarioId") Integer usuarioId);
}

