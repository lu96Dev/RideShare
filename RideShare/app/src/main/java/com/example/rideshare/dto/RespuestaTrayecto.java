package com.example.rideshare.dto;

public class RespuestaTrayecto {
    private Integer id;
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private String descripcion;
    private Double origenLat;
    private Double origenLng;
    private Boolean activo;
    private Integer conductorId;

    // Constructor vacío requerido por Gson/Retrofit
    public RespuestaTrayecto() {}

    // Getters (mínimos necesarios)
    public Integer getId() { return id; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    // ... resto de getters
}