package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    @Query("SELECT m FROM Mensaje m WHERE m.trayectoId = :trayectoId AND (m.remitenteId = :usuarioId OR m.destinatarioId = :usuarioId) ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findMensajesByTrayectoAndUsuario(@Param("trayectoId") Integer trayectoId, @Param("usuarioId") Integer usuarioId);

}
