package com.example.rideshare.model;

public class Mensaje {

    private String texto;
    private String usuario;
    private String receptor;
    private long fecha;

    public Mensaje() {
    }

    public Mensaje(String texto, String usuario, String receptor) {
        this.texto = texto;
        this.usuario = usuario;
        this.receptor = receptor;
        this.fecha = System.currentTimeMillis();
    }

    public String getTexto() {
        return texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getReceptor() {
        return receptor;
    }

    public long getFecha() {
        return fecha;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
