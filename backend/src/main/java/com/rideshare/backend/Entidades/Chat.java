package com.rideshare.backend.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"trayecto_id", "usuario1_id", "usuario2_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "trayecto_id", nullable = false)
    private Integer trayectoId;

    @Column(name = "usuario1_id", nullable = false)
    private Integer usuario1Id;

    @Column(name = "usuario2_id", nullable = false)
    private Integer usuario2Id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
