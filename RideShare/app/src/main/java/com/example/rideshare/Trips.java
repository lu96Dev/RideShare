package com.example.rideshare;

import com.google.gson.annotations.SerializedName;

public class Trips {
    // Mapeo explícito del objeto conductor que envía el backend
    @SerializedName("conductor")
    private Conductor conductor;

    private String descripcion;
    private String hora;
    private String tiempo;

    @SerializedName("origenLat")
    private Double latitud;

    @SerializedName("origenLng")
    private Double longitud;

    private String distancia;

    public Trips() {}

    public String getNombre() {
        return (conductor != null) ? conductor.nombre : "Anónimo";
    }

    public String getApellidos() {
        return (conductor != null) ? conductor.apellidos : "";
    }

    public String getDescripcion() { return descripcion; }
    public String getHora() { return hora; }
    public String getTiempo() { return tiempo; }
    public String getDistancia() { return distancia; }
    public Double getLatitud() { return latitud; }
    public Double getLongitud() { return longitud; }

    public void setDistancia(String distancia) { this.distancia = distancia; }

    public static class Conductor {
        // Asegúrate de que en tu clase Usuario del backend se llamen así
        public String nombre;
        public String apellidos;
    }
}