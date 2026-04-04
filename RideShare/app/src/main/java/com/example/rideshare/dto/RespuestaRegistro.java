package com.example.rideshare.dto;

public class RespuestaRegistro {
    private boolean correcto;
    private String mensaje;
    private Integer id;
    private String nombre;
    private String email;

    public boolean esCorrecto() {
        return correcto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}