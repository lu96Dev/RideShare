package com.example.rideshare.dto;

public class RespuestaRegistro {
    private boolean correcto;
    private String mensaje;

    public boolean esCorrecto() {
        return correcto;
    }

    public String getMensaje() {
        return mensaje;
    }
}