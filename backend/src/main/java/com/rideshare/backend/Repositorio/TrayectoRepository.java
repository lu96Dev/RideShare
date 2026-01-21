package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Trayecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {

    List<Trayecto> findByConductorId(Integer conductorId);

    List<Trayecto> findByActivoTrue();

    @Query("""
        SELECT t FROM Trayecto t
        WHERE LOWER(t.origen) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR LOWER(t.destino) LIKE LOWER(CONCAT('%', :busqueda, '%'))
    """)
    List<Trayecto> buscarTrayectos(@Param("busqueda") String busqueda);
}
