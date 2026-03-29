package com.example.rideshare;

public class Trips {
    private String nombre;
    private String tiempo;
    private String descripcion;
    private String hora;

    public Trips(String nombre, String tiempo, String descripcion, String hora) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    // Getters necesarios para el Adaptador
    public String getNombre() { return nombre; }
    public String getTiempo() { return tiempo; }
    public String getDescripcion() { return descripcion; }
    public String getHora() { return hora; }
}