package com.example.rideshare.data.dto;

public class SolicitudTrayecto {
    private String origen;
    private Double origenLat;
    private Double origenLng;
    private String destino; // Campo añadido obligatorio
    private String fecha;   // Campo añadido obligatorio
    private String hora;
    private String descripcion;

    // Constructor
    public SolicitudTrayecto(String origen, Double origenLat, Double origenLng,
                             String destino, String fecha, String hora, String descripcion) {
        this.origen = origen;
        this.origenLat = origenLat;
        this.origenLng = origenLng;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }
}