package com.rideshare.backend.Entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "trayectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trayecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "conductor_id", nullable = false)
    private Integer conductorId;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Column(name = "latitud_origen", precision = 10, scale = 8)
    private BigDecimal latitudOrigen;

    @Column(name = "longitud_origen", precision = 11, scale = 8)
    private BigDecimal longitudOrigen;

    @Column(name = "latitud_destino", precision = 10, scale = 8)
    private BigDecimal latitudDestino;

    @Column(name = "longitud_destino", precision = 11, scale = 8)
    private BigDecimal longitudDestino;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "plazas_disponibles", nullable = false)
    private Integer plazasDisponibles;

    @Column(name = "plazas_totales", nullable = false)
    private Integer plazasTotales;

    @Column(name = "costo_estimado", precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
