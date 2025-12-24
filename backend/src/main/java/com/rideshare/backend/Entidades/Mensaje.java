package com.rideshare.backend.Entidades;

public class Mensaje {
    String id;
    String texto;
    String fechaHora;

    public Mensaje(){

    }
    public Mensaje(String id, String texto, String fechaHora) {
        this.id = id;
        this.texto = texto;
        this.fechaHora = fechaHora;
    }

    public void enviar(){

    }
    public void eliminar(){

    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id='" + id + '\'' +
                ", texto='" + texto + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                '}';
    }
}
