package com.example.rideshare.dto;

public class SolicitudInicio {
    private String correo;
    private String contrasenia;

    public SolicitudInicio(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }
}
