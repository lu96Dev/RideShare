package com.example.rideshare.dto;

public class RespuestaInicio {
    private boolean correcto;
    private String mensaje;
    private Integer id;
    private String nombre;
    private String email;

    private String biografia;
    public boolean esCorrecto() {
        return correcto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Integer getId() {
        return id;
    }
    public String getBiografia() {return biografia;}
}