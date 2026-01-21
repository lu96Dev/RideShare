package com.rideshare.backend.Entidades;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "remitente_id", nullable = false)
    private Integer remitenteId;

    @Column(name = "destinatario_id", nullable = false)
    private Integer destinatarioId;

    @Column(name = "trayecto_id", nullable = false)
    private Integer trayectoId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean leido = false;
}
