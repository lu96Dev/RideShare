package com.example.rideshare.dto;

public class RespuestaInicio {
    private boolean correcto;
    private String mensaje;

    public boolean esCorrecto() {
        return correcto;
    }

    public String getMensaje() {
        return mensaje;
    }
}
