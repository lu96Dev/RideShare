package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    List<Notificacion> findByUsuarioIdOrderByFechaEnvioDesc(Integer usuarioId);

    List<Notificacion> findByUsuarioIdAndLeida(Integer usuarioId, Boolean leida);

    @Query("""
        SELECT n FROM Notificacion n
        WHERE n.usuarioId = :usuarioId
          AND n.leida = false
        ORDER BY n.fechaEnvio DESC
    """)
    List<Notificacion> findNotificacionesNoLeidas(@Param("usuarioId") Integer usuarioId);

    @Query("""
        SELECT COUNT(n)
        FROM Notificacion n
        WHERE n.usuarioId = :usuarioId
          AND n.leida = false
    """)
    Long countNotificacionesNoLeidas(@Param("usuarioId") Integer usuarioId);

    @Modifying
    @Query("""
        UPDATE Notificacion n
        SET n.leida = true
        WHERE n.usuarioId = :usuarioId
          AND n.leida = false
    """)
    void marcarTodasComoLeidas(@Param("usuarioId") Integer usuarioId);

    @Modifying
    @Query("""
        UPDATE Notificacion n
        SET n.leida = true
        WHERE n.id = :notificacionId
    """)
    void marcarComoLeida(@Param("notificacionId") Integer notificacionId);

    @Modifying
    @Query("""
        DELETE FROM Notificacion n
        WHERE n.usuarioId = :usuarioId
          AND n.leida = true
          AND n.fechaEnvio < :fechaLimite
    """)
    void eliminarNotificacionesAntiguas(
            @Param("usuarioId") Integer usuarioId,
            @Param("fechaLimite") LocalDateTime fechaLimite
    );
}