package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeriaRepository extends JpaRepository<Mensaje, Integer> {

    @Query("""
        SELECT m FROM Mensaje m
        WHERE m.trayectoId = :trayectoId
        AND (m.remitenteId = :usuarioId OR m.destinatarioId = :usuarioId)
        ORDER BY m.fechaEnvio ASC
        """)
    List<Mensaje> findMensajesByTrayectoAndUsuario(
            @Param("trayectoId") Integer trayectoId,
            @Param("usuarioId") Integer usuarioId
    );

    @Query("""
        SELECT m FROM Mensaje m
        WHERE m.remitenteId = :usuarioId OR m.destinatarioId = :usuarioId
        GROUP BY m.trayectoId
        ORDER BY MAX(m.fechaEnvio) DESC
        """)
    List<Mensaje> findChats(@Param("usuarioId") Integer usuarioId);

    @Query("""
        SELECT m FROM Mensaje m
        WHERE m.trayectoId = :trayectoId
        AND (m.remitenteId = :usuarioId OR m.destinatarioId = :usuarioId)
        AND m.id > :ultimoId
        ORDER BY m.fechaEnvio ASC
        """)
    List<Mensaje> findMensajesNuevos(
            @Param("trayectoId") Integer trayectoId,
            @Param("usuarioId") Integer usuarioId,
            @Param("ultimoId") Integer ultimoId
    );


    @Query("""
    SELECT m FROM Mensaje m
    WHERE (m.remitenteId = :usuarioId OR m.destinatarioId = :usuarioId)
    AND m.fechaEnvio IN (
        SELECT MAX(m2.fechaEnvio)
        FROM Mensaje m2
        WHERE (m2.remitenteId = :usuarioId OR m2.destinatarioId = :usuarioId)
        GROUP BY m2.trayectoId,
                 CASE 
                    WHEN m2.remitenteId = :usuarioId THEN m2.destinatarioId
                    ELSE m2.remitenteId
                 END
            )
            ORDER BY m.fechaEnvio DESC
        """)
    List<Mensaje> findChatsUsuario(@Param("usuarioId") Integer usuarioId);
}

