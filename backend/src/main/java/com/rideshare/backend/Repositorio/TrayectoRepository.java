package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Trayecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {

    List<Trayecto> findByConductorId(Integer conductorId);

    @Query("""
        SELECT t FROM Trayecto t
        WHERE t.fecha >= :fecha
          AND t.plazasDisponibles > 0
    """)
    List<Trayecto> findTrayectosDisponibles(@Param("fecha") LocalDate fecha);

    @Query("""
        SELECT t FROM Trayecto t
        WHERE LOWER(t.origen) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR LOWER(t.destino) LIKE LOWER(CONCAT('%', :busqueda, '%'))
    """)
    List<Trayecto> buscarTrayectos(@Param("busqueda") String busqueda);
}
