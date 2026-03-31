package com.example.rideshare.dto;

public class SolicitudCambioPassword {
    private String passwordActual;
    private String passwordNueva;

    public SolicitudCambioPassword(String passwordActual, String passwordNueva) {
        this.passwordActual = passwordActual;
        this.passwordNueva = passwordNueva;
    }

    public String getPasswordActual() { return passwordActual; }
    public String getPasswordNueva() { return passwordNueva; }
}
