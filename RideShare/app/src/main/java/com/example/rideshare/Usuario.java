package com.example.rideshare;

public class Usuario {
    private String nombre;
    private String disponibilidad;
    private String horaSalida;
    private int fotoResId;

    public Usuario(String nombre, String disponibilidad, String horaSalida, int fotoResId) {
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.horaSalida = horaSalida;
        this.fotoResId = fotoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public int getFotoResId() {
        return fotoResId;
    }
}
