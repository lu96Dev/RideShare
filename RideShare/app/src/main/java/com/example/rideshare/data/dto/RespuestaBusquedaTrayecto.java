package com.example.rideshare.data.dto;

import com.example.rideshare.model.Trip;
import java.util.List;

public class RespuestaBusquedaTrayecto {
    private boolean exito;
    private String mensaje;
    private List<Trip> trayectosEncontrados;

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public List<Trip> getTrayectosEncontrados() { return trayectosEncontrados; }
    public void setTrayectosEncontrados(List<Trip> trayectosEncontrados) { this.trayectosEncontrados = trayectosEncontrados; }
}