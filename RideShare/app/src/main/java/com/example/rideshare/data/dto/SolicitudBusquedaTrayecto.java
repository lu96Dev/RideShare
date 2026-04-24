package com.example.rideshare.data.dto;

public class SolicitudBusquedaTrayecto {
    private Double latitud;
    private Double longitud;
    private Double radioKm; // El radio lógico de recogida (ej: 5.0 km)

    public SolicitudBusquedaTrayecto(Double latitud, Double longitud, Double radioKm) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.radioKm = radioKm;
    }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Double getRadioKm() { return radioKm; }
    public void setRadioKm(Double radioKm) { this.radioKm = radioKm; }
}