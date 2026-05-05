package com.rideshare.backend.TransferenciaDatos;

import com.rideshare.backend.Entidades.Trayecto;
import java.util.List;

public class BusquedaTrayectoResponse {
    private boolean exito;
    private String mensaje;
    private List<Trayecto> trayectosEncontrados;

    public BusquedaTrayectoResponse(boolean exito, String mensaje, List<Trayecto> trayectosEncontrados) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.trayectosEncontrados = trayectosEncontrados;
    }

    // Getters y Setters
    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public List<Trayecto> getTrayectosEncontrados() { return trayectosEncontrados; }
    public void setTrayectosEncontrados(List<Trayecto> trayectosEncontrados) { this.trayectosEncontrados = trayectosEncontrados; }
}