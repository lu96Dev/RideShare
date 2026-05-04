package com.example.rideshare.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Trip implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("conductorId")
    private Integer conductorId;

    @SerializedName("conductor")
    private Conductor conductor;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("hora")
    private String hora;

    @SerializedName("tiempo")
    private String tiempo;

    @SerializedName("origenLat")
    private Double latitud;

    @SerializedName("origenLng")
    private Double longitud;

    @SerializedName("distancia")
    private String distancia;

    // =====================
    // GETTERS
    // =====================

    public Integer getId() {
        return id;
    }

    public Integer getConductorId() {
        return conductorId;
    }

    public String getNombre() {
        return conductor != null ? conductor.nombre : "Anónimo";
    }

    public String getApellidos() {
        return conductor != null ? conductor.apellidos : "";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHora() {
        return hora;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getDistancia() {
        return distancia;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    // =====================
    // SETTERS
    // =====================

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    // =====================
    // CLASE INTERNA
    // =====================

    public static class Conductor implements Serializable {
        public String nombre;
        public String apellidos;
    }
}