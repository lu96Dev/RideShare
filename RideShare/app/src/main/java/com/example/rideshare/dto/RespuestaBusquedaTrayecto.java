package com.example.rideshare.dto;

import com.example.rideshare.Trips;
import java.util.List;

public class RespuestaBusquedaTrayecto {
    private boolean exito;
    private String mensaje;
    private List<Trips> trayectosEncontrados;

    // Getters y Setters
    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public List<Trips> getTrayectosEncontrados() { return trayectosEncontrados; }
    public void setTrayectosEncontrados(List<Trips> trayectosEncontrados) { this.trayectosEncontrados = trayectosEncontrados; }
}