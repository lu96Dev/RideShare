package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Trayecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {

    List<Trayecto> findByConductorId(Integer conductorId);

    List<Trayecto> findByActivoTrue();

    @Query("""
        SELECT t FROM Trayecto t
        WHERE LOWER(t.origen) LIKE LOWER(CONCAT('%', :busqueda, '%'))
           OR LOWER(t.destino) LIKE LOWER(CONCAT('%', :busqueda, '%'))
    """)
    List<Trayecto> buscarTrayectos(@Param("busqueda") String busqueda);

    @Query("""
        SELECT t FROM Trayecto t 
        JOIN FETCH t.conductor 
        WHERE t.activo = true AND (
            6371 * acos(
                cos(radians(:lat)) * cos(radians(t.origenLat)) * cos(radians(t.origenLng) - radians(:lng)) + 
                sin(radians(:lat)) * sin(radians(t.origenLat))
            )
        ) <= :radio
    """)
    List<Trayecto> encontrarTrayectosEnRadio(
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radio") Double radio);
}