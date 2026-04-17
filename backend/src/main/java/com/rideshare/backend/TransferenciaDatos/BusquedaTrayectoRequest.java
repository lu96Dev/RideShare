package com.rideshare.backend.TransferenciaDatos;

public class BusquedaTrayectoRequest {
    private Double latitud;
    private Double longitud;
    private Double radioKm;

    // Getters y Setters
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Double getRadioKm() { return radioKm; }
    public void setRadioKm(Double radioKm) { this.radioKm = radioKm; }
}