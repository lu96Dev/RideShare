package com.example.rideshare.dto;

public class SolicitudRegistro {
    private String correo;
    private String contrasenia;

    public SolicitudRegistro(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
}
