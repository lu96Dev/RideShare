package com.rideshare.backend.Repositorio;

import com.rideshare.backend.Entidades.Trayecto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TrayectoRepository extends JpaRepository<Trayecto, Integer> {

    List<Trayecto> findByFechaAfterAndEstado(LocalDate fecha, String estado);

}
