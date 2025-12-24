package com.rideshare.backend.Entidades;

public class Trayecto {
    String id;
    String origen;
    String destino;
    String fechaHora;

    public Trayecto(){

    }

    public Trayecto(String fechaHora, String destino, String origen, String id) {
        this.fechaHora = fechaHora;
        this.destino = destino;
        this.origen = origen;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Override
    public String toString() {
        return "Trayecto{" +
                "id='" + id + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}
