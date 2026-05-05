package com.rideshare.backend.TransferenciaDatos;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class TrayectoRequest {

    private String origen;
    private Double origenLat;
    private Double origenLng;
    private String destino;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;

}
