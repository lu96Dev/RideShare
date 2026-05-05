package com.example.rideshare.model;

public class ChatRequest {

    private Integer trayectoId;
    private Integer usuario1Id;
    private Integer usuario2Id;

    public Integer getTrayectoId() { return trayectoId; }
    public void setTrayectoId(Integer trayectoId) { this.trayectoId = trayectoId; }

    public Integer getUsuario1Id() { return usuario1Id; }
    public void setUsuario1Id(Integer usuario1Id) { this.usuario1Id = usuario1Id; }

    public Integer getUsuario2Id() { return usuario2Id; }
    public void setUsuario2Id(Integer usuario2Id) { this.usuario2Id = usuario2Id; }
}
